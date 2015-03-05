package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DaireBelgeDataModel;
import com.ronin.managed.bean.lazydatamodel.DuyuruDataModel;
import com.ronin.model.DaireBelge;
import com.ronin.model.Duyuru;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Belge;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.BelgeSorguKriteri;
import com.ronin.model.kriter.IlanSorguKriteri;
import com.ronin.service.IFileUploadService;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "belgeMB")
@ViewScoped
public class BelgeMB implements Serializable {

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

    //combolar
    private List<IAbstractEntity> blokList;
    private List<IAbstractEntity> belgeTipiList;

    //belgeler
    private DaireBelge selectedDaireBelge;
    private DaireBelgeDataModel belgeDataModel;
    private DefaultStreamedContent download;
    public boolean istamamlandiMi;
    private Belge yeniBelge = new Belge();
    private boolean skip;


    @PostConstruct
    public void init() {
        prepareCombos();
        setUserRolInfos();
    }

    public void getBelgeListBySorguKriteri() {
        List<DaireBelge> dataList = fileUploadService.getBelgeListBySorguSonucu(0, 100, sorguKriteri, sessionInfo);
        dataModel = new DaireBelgeDataModel(dataList);
    }

    public void prepareCombos() {
        dataModel = null;
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket",sessionInfo);
        belgeTipiList = ortakService.getListByNamedQuery("BelgeTipi.findAll");
    }

    public void setUserRolInfos() {
        if (!sessionInfo.isAdminMi()) {
            sorguKriteri.setKullanici(sessionInfo.getKullanici());
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

    public void deleteBelge() {
        try {
            fileUploadService.belgeSil(selectedDaireBelge);
            JsfUtil.addSuccessMessage(message.getString("belge_silme_basarili"));
            getBelgeListBySorguKriteri();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
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

    public List<IAbstractEntity> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<IAbstractEntity> blokList) {
        this.blokList = blokList;
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
}
