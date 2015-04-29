package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DaireBelgeDataModel;
import com.ronin.model.Daire;
import com.ronin.model.DaireBelge;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Belge;
import com.ronin.model.kriter.BelgeSorguKriteri;
import com.ronin.service.IFileUploadService;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "belgeIslemleriMB")
@ViewScoped
public class BelgeIslemleriMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{fileUploadService}")
    private IFileUploadService fileUploadService;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    private BelgeSorguKriteri sorguKriteri = new BelgeSorguKriteri();

    private DaireBelge selected;

    private DaireBelgeDataModel dataModel;
    private Daire selectedDaire;

    //combolar
    private List<IAbstractEntity> belgeTipiList;

    //belgeler
    private DaireBelge selectedDaireBelge;
    private DaireBelgeDataModel belgeDataModel;
    private DefaultStreamedContent download;
    public boolean istamamlandiMi;
    private Belge yeniBelge = new Belge();
    private boolean skip;
    private UploadedFile uploadedFile;
    private List<UploadedFile> uploadedFileList = new ArrayList<>();

    private String destination = "C:\\rmsFileUploadDocument\\";


    @PostConstruct
    public void init() {
        getFlushObjects();
        prepareCombos();
    }

    public void getFlushObjects() {
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
        selectedDaire = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaire");
    }

    public void getBelgeListBySorguKriteri() {
        List<DaireBelge> dataList = fileUploadService.getBelgeListBySorguSonucu(0, 100, sorguKriteri, sessionInfo);
        dataModel = new DaireBelgeDataModel(dataList);
    }

    public void prepareCombos() {
        dataModel = null;
        belgeTipiList = ortakService.getListByNamedQuery("BelgeTipi.findAll");
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    //belge islemleri
    public DefaultStreamedContent getDownload() {
        try {
            InputStream input = selectedDaireBelge.getBelge().getContent().getBinaryStream();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            download = new DefaultStreamedContent(input, externalContext.getMimeType(selectedDaireBelge.getBelge().getDataName()), selectedDaireBelge.getBelge().getDataName());
            return download;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.toString());
        }
        return null;
    }

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public void prepDownload() throws Exception {
        InputStream input = selectedDaireBelge.getBelge().getContent().getBinaryStream();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(selectedDaireBelge.getBelge().getDataName()), selectedDaireBelge.getBelge().getDataName()));
    }

    public void deleteBelge(DaireBelge selectedDaireBelge) {
        try {
            setSelectedDaireBelge(selectedDaireBelge);
            fileUploadService.belgeSil(selectedDaireBelge);
            JsfUtil.addSuccessMessage(message.getString("belge_silme_basarili"));
            getBelgeListBySorguKriteri();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void belgeUpload(FileUploadEvent event) {
        if (event.getFile().equals(null)) {
            JsfUtil.addSuccessMessage("file is null");
        }
        try {
            uploadedFile = event.getFile();
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            uploadedFileList.add(uploadedFile);
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("error reading file " + e);
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String belgeKadet() {
        for (UploadedFile file : uploadedFileList) {
            if (selectedDaire != null) {
                fileUploadService.daireBelgeEkle(sessionInfo, selectedDaire, file, yeniBelge);
            } else {
                fileUploadService.belgeEkle(sessionInfo, file, yeniBelge);
            }
        }
        JsfUtil.addSuccessMessage(message.getString("belge_ekleme_basarili"));
        return geriDon();
    }

    public void deleteTempBelgeList(UploadedFile selectedUploadedFile) {
        uploadedFileList.remove(selectedUploadedFile);
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireObject", selectedDaire);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public Belge getYeniBelge() {
        return yeniBelge;
    }

    public void setYeniBelge(Belge yeniBelge) {
        this.yeniBelge = yeniBelge;
    }

    public boolean isIstamamlandiMi() {
        return istamamlandiMi;
    }

    public void setIstamamlandiMi(boolean istamamlandiMi) {
        this.istamamlandiMi = istamamlandiMi;
    }

    public DaireBelgeDataModel getBelgeDataModel() {
        return belgeDataModel;
    }

    public void setBelgeDataModel(DaireBelgeDataModel belgeDataModel) {
        this.belgeDataModel = belgeDataModel;
    }

    public DaireBelge getSelectedDaireBelge() {
        return selectedDaireBelge;
    }

    public void setSelectedDaireBelge(DaireBelge selectedDaireBelge) {
        this.selectedDaireBelge = selectedDaireBelge;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }

    public ResourceBundle getLabel() {
        return label;
    }

    public void setLabel(ResourceBundle label) {
        this.label = label;
    }

    public IFileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public void setFileUploadService(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    public BelgeSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(BelgeSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public DaireBelge getSelected() {
        return selected;
    }

    public void setSelected(DaireBelge selected) {
        this.selected = selected;
    }

    public DaireBelgeDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DaireBelgeDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public List<IAbstractEntity> getBelgeTipiList() {
        return belgeTipiList;
    }

    public void setBelgeTipiList(List<IAbstractEntity> belgeTipiList) {
        this.belgeTipiList = belgeTipiList;
    }

    public Daire getSelectedDaire() {
        return selectedDaire;
    }

    public void setSelectedDaire(Daire selectedDaire) {
        this.selectedDaire = selectedDaire;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFileList() {
        return uploadedFileList;
    }

    public void setUploadedFileList(List<UploadedFile> uploadedFileList) {
        this.uploadedFileList = uploadedFileList;
    }
}
