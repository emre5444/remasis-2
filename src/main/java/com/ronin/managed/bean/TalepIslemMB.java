package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DaireBelgeDataModel;
import com.ronin.managed.bean.lazydatamodel.TalepDataModel;
import com.ronin.model.*;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Belge;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.BilgilendirmeTipi;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.kriter.TalepSorguKriteri;
import com.ronin.service.IFileUploadService;
import com.ronin.service.ITalepService;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.primefaces.context.RequestContext;
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
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@ManagedBean(name = "talepIslemMB")
@ViewScoped
public class TalepIslemMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(TalepIslemMB.class);
    //servisler
    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{talepService}")
    private ITalepService talepService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    @ManagedProperty("#{fileUploadService}")
    private IFileUploadService fileUploadService;

    //sorgu kriterleri
    private TalepSorguKriteri sorguKriteri = new TalepSorguKriteri();

    //daire islemleri
    private TalepDaire selected;
    private DaireBelge selectedDaireBelge;
    private Daire selectedDaire;

    private TalepDataModel dataModel;
    private DaireBelgeDataModel belgeDataModel;


    //bildirim tipi
    public BildirimTipi.ENUM bildirimTipi;

    //combolar
    private List<Kullanici> kullaniciList;
    private List<TalepTipi> talepTipiList;

    //talepler
    private ArizaTalebi yeniArizaTalep = new ArizaTalebi();
    private ItirazTalebi yeniItiraz = new ItirazTalebi();
    private SikayetTalebi yeniSikayetTalebi = new SikayetTalebi();
    private BelgeTalebi yeniBelgeTalebi = new BelgeTalebi();
    private DaireBorc selectedBorc;

    private Belge yeniBelge = new Belge();
    private List<IAbstractEntity> belgeTipiList;
    private boolean skip;


    TalepSorguKriteri sk;

    @PostConstruct
    public void init() {
        getFlushObjects();
        preparePage();
    }

    public void getFlushObjects() {
        selectedBorc = (DaireBorc) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedBorc");
        selected = (TalepDaire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedTalepDaireObject");
        selectedDaire = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaire");
        Object sk = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("sorguKriteri");
        if (sk instanceof TalepSorguKriteri)
            sorguKriteri = (TalepSorguKriteri) sk;
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
    }

    public void preparePage() {
        belgeTipiList = ortakService.getListByNamedQuery("BelgeTipi.findAll");
        if (selected != null && selected.getTalep().getTalepTipi().isBelgeTalebiMi()) {
            getBelgeListByTalep();
        }
    }


    public List<Kullanici> completePlayer(String query) {
        List<Kullanici> suggestions = ortakService.getKullaniciByName(query, sessionInfo);
        return suggestions;
    }

    public List<DaireBorc> getItirazEdilenDaireBorcAsList() {
        return talepService.getItirazEdilenDaireBorcAsList(((ItirazTalebi) selected.getTalep()).getDaireBorc());
    }

    public void getBelgeListByTalep() {
        List<DaireBelge> belgeList = fileUploadService.getBelgeListByTalep(selected.getTalep());
        belgeDataModel = new DaireBelgeDataModel(belgeList);
    }

    public String talepOnayla() {
        talepOnaylaAction();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sk);
        return "talepSorgula.xhtml";
    }

    public void talepOnaylaAction() {
        try {
            talepService.talepOrtakOnayla(selected, sessionInfo);
            bildirimGonderForOnay();
            JsfUtil.addSuccessMessage(message.getString("talep_onaylama_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public String talepReddet() {
        talepReddetAction();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sk);
        return "talepSorgula.xhtml";
    }

    public void talepReddetAction() {
        try {
            talepService.talepOrtakReddet(selected, sessionInfo);
            bildirimGonderForRet();
            JsfUtil.addSuccessMessage(message.getString("talep_reddetme_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public void bildirimGonderForOnay() {
        if (selected.getTalep().getTalepTipi().isArizaTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.ARIZA;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("ariza_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("ariza_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("ariza_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("ariza_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isItirazTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.AIDAT_ITIRAZ;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("itiraz_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("itiraz_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("itiraz_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("itiraz_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isSikayetTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.SIKAYET;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("sikayet_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("sikayet_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("sikayet_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("sikayet_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isBelgeTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.BELGE_TALEBI;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("belge_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("belge_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("belge_talebi_onaylama_bildirim"), selected.getTalep().getId() + message.getString("belge_talebi_onaylama_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        }
    }

    public void bildirimGonderForRet() {
        if (selected.getTalep().getTalepTipi().isArizaTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.ARIZA;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("ariza_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("ariza_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("ariza_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("ariza_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isItirazTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.AIDAT_ITIRAZ;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("itiraz_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("itiraz_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("itiraz_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("itiraz_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isSikayetTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.SIKAYET;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("sikayet_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("sikayet_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("sikayet_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("sikayet_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isBelgeTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.BELGE_TALEBI;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("belge_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("belge_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("belge_talebi_reddetme_bildirim"), selected.getTalep().getId() + message.getString("belge_talebi_reddetme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        }
    }

    //belge i?lemleri
    public DefaultStreamedContent getDownload() {
        try {
            InputStream input = selectedDaireBelge.getBelge().getContent().getBinaryStream();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            return new DefaultStreamedContent(input, externalContext.getMimeType(selectedDaireBelge.getBelge().getDataName()), selectedDaireBelge.getBelge().getDataName());

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.toString());
        }
        return null;
    }

    public void deleteBelge() {
        try {
            fileUploadService.belgeSil(selectedDaireBelge);
            getBelgeListByTalep();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void clearBelgeYuklemeObject() {
        yeniBelge = new Belge();
    }

    public String navigateToBelgeEkle() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireTalep", selected);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backPage", "/pages/talepIslemleri/talepOnaylama.xhtml");
        return "/pages/sitePaylasimIslemleri/belgeEkleme.xhtml";
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public String yeniArizaTalebiEkleme() {
        talepService.arizaTalebiEkleme(selectedDaire, yeniArizaTalep, sessionInfo);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ARIZA, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ARIZA, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        return getBackPage();
    }

    public String yeniItirazTalebiEkleme() {
        talepService.itirazTalebiEkleme(selectedDaire, yeniItiraz, sessionInfo, selectedBorc);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.AIDAT_ITIRAZ, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.AIDAT_ITIRAZ, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        return getBackPage();
    }

    public String yeniSikayetTalebiEkleme() {
        talepService.sikayetTalebiEkleme(selectedDaire, yeniSikayetTalebi, sessionInfo);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.SIKAYET, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.SIKAYET, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        return getBackPage();
    }

    public String yeniBelgeTalebiEkleme() {
        talepService.belgeTalebiEkleme(selectedDaire, yeniBelgeTalebi, sessionInfo);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.BELGE_TALEBI, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.BELGE_TALEBI, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        return getBackPage();
    }

    //page navigations
    public String daireGoruntule() {
        return "daireGoruntule";
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireObject", selectedDaire);
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }

    //getter and setters
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

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public ITalepService getTalepService() {
        return talepService;
    }

    public void setTalepService(ITalepService talepService) {
        this.talepService = talepService;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public TalepSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(TalepSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public TalepDaire getSelected() {
        return selected;
    }

    public void setSelected(TalepDaire selected) {
        this.selected = selected;
    }

    public TalepDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(TalepDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public List<Kullanici> getKullaniciList() {
        return kullaniciList;
    }

    public void setKullaniciList(List<Kullanici> kullaniciList) {
        this.kullaniciList = kullaniciList;
    }

    public List<TalepTipi> getTalepTipiList() {
        return talepTipiList;
    }

    public void setTalepTipiList(List<TalepTipi> talepTipiList) {
        this.talepTipiList = talepTipiList;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public IFileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public void setFileUploadService(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
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

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<IAbstractEntity> getBelgeTipiList() {
        return belgeTipiList;
    }

    public void setBelgeTipiList(List<IAbstractEntity> belgeTipiList) {
        this.belgeTipiList = belgeTipiList;
    }

    public Belge getYeniBelge() {
        return yeniBelge;
    }

    public void setYeniBelge(Belge yeniBelge) {
        this.yeniBelge = yeniBelge;
    }

    public ArizaTalebi getYeniArizaTalep() {
        return yeniArizaTalep;
    }

    public void setYeniArizaTalep(ArizaTalebi yeniArizaTalep) {
        this.yeniArizaTalep = yeniArizaTalep;
    }

    public TalepSorguKriteri getSk() {
        return sk;
    }

    public void setSk(TalepSorguKriteri sk) {
        this.sk = sk;
    }

    public DaireBorc getSelectedBorc() {
        return selectedBorc;
    }

    public void setSelectedBorc(DaireBorc selectedBorc) {
        this.selectedBorc = selectedBorc;
    }

    public BelgeTalebi getYeniBelgeTalebi() {
        return yeniBelgeTalebi;
    }

    public void setYeniBelgeTalebi(BelgeTalebi yeniBelgeTalebi) {
        this.yeniBelgeTalebi = yeniBelgeTalebi;
    }

    public SikayetTalebi getYeniSikayetTalebi() {
        return yeniSikayetTalebi;
    }

    public void setYeniSikayetTalebi(SikayetTalebi yeniSikayetTalebi) {
        this.yeniSikayetTalebi = yeniSikayetTalebi;
    }

    public ItirazTalebi getYeniItiraz() {
        return yeniItiraz;
    }

    public void setYeniItiraz(ItirazTalebi yeniItiraz) {
        this.yeniItiraz = yeniItiraz;
    }

    public BildirimTipi.ENUM getBildirimTipi() {
        return bildirimTipi;
    }

    public void setBildirimTipi(BildirimTipi.ENUM bildirimTipi) {
        this.bildirimTipi = bildirimTipi;
    }

    public Daire getSelectedDaire() {
        return selectedDaire;
    }

    public void setSelectedDaire(Daire selectedDaire) {
        this.selectedDaire = selectedDaire;
    }
}
