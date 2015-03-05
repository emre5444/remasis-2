package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.*;
import com.ronin.model.*;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.*;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.TalepSorguKriteri;
import com.ronin.model.sorguSonucu.BorcAlacakViewBean;
import com.ronin.service.IDaireService;
import com.ronin.service.IFileUploadService;
import com.ronin.service.IFinansalIslemlerService;
import com.ronin.service.ITalepService;
import com.ronin.utils.DateUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "daireGoruntuleMB")
@ViewScoped
public class DaireGoruntuleMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireGoruntuleMB.class);
    //servisler
    @ManagedProperty("#{daireService}")
    private IDaireService daireService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{talepService}")
    private ITalepService talepService;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    @ManagedProperty("#{fileUploadService}")
    private IFileUploadService fileUploadService;

    @ManagedProperty("#{finansalIslemlerService}")
    private IFinansalIslemlerService finansalIslemlerService;

    //sorgu kriterleri
    private DaireSorguKriteri sorguKriteri = new DaireSorguKriteri();
    private TalepSorguKriteri talepSorguKriteri = new TalepSorguKriteri();

    //talep islemleri
    private ArizaTalebi yeniArizaTalep = new ArizaTalebi();
    private ItirazTalebi yeniItiraz = new ItirazTalebi();
    private SikayetTalebi yeniSikayetTalebi = new SikayetTalebi();
    private BelgeTalebi yeniBelgeTalebi = new BelgeTalebi();
    private TalepDaire selectedTalep;
    private TalepDataModel talepDatamodel;
    private String talepIslem;

    //daire islemleri
    private Daire selected;
    private DaireDataModel dataModel;
    private String islem;
    List<Daire> daireSorguSonucu;

    //duyuru islemleri

    private Duyuru yeniDuyuru = new Duyuru();
    private Duyuru selectedDuyuru;
    private DuyuruDataModel duyuruDataModel;

    //aidat islemleri
    private DaireBorc selectedBorc;
    private BorcDataModel borcDataModel;
    private String toplamBorcTutar;
    private String toplamOdenenTutar;
    private String toplamBakiyeTutar;

    //kullanici daire islemleri
    private KullaniciDaireDataModel kullaniciDaireDataModel;
    private Kullanici selectedKullanici;
    private KullaniciDaire selectedKullaniciDaire;

    //combolar
    private List<Kullanici> kullaniciList;

    //belgeler
    private DaireBelge selectedDaireBelge;
    private DaireBelgeDataModel belgeDataModel;
    private DefaultStreamedContent download;
    public boolean istamamlandiMi;
    private Belge yeniBelge = new Belge();
    private List<IAbstractEntity> belgeTipiList;
    private boolean skip;

    //chart
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;

    private List<BorcAlacakViewBean> borcAlacakViewBeanList;
    private List<BorcAlacakViewBean> sonDonemborcAlacakViewBeanList;

    //load booleans
    boolean aidatLoaded = false;
    boolean finansLoaded = false;
    boolean belgeLoaded = false;
    boolean ilanLoaded = false;
    boolean talepLoaded = false;
    boolean sakinLoaded = false;

    @PostConstruct
    public void init() {
        getFlushObjects();
        setUserRolInfos();
        prepareDummyLoadObjects();
    }

    public void prepareDummyLoadObjects() {
        aidatLoaded = false;
        finansLoaded = false;
        belgeLoaded = false;
        ilanLoaded = false;
        talepLoaded = false;
        sakinLoaded = false;
    }

    public void getFlushObjects() {
        selected = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireObject");
        belgeTipiList = ortakService.getListByNamedQuery("BelgeTipi.findAll");
    }

    public void handleDaireIslem() {
        if (talepIslem.equals("arizaTalebiGiris")) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('talepEklePopup').show()");
        } else if (talepIslem.equals("sikayetTalebiGiris")) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('sikayetTalebiPopup').show()");
        } else if (talepIslem.equals("belgeTalebiGiris")) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('belgeTalebiPopup').show()");
        }
        talepIslem = null;
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equals("daire_detay")) {
            getDaireBilgileriTabInfos();
        } else if (event.getTab().getId().equals("aidat_bilgileri")) {
            getBorcTabInfos();
        } else if (event.getTab().getId().equals("daire_duyurular")) {
            getDuyuruTabInfos();
        } else if (event.getTab().getId().equals("daire_talepler")) {
            getTalepTabInfos();
        } else if (event.getTab().getId().equals("daire_belgeler")) {
            getBelgelerTabInfos();
        }
    }

    public void getTalepTabInfos() {
        talepSorguKriteri.setDaireKodu(selected.getDaireKodu());
        List<TalepDaire> dataList = talepService.getListCriteriaForPaging(0, 100, talepSorguKriteri, sessionInfo);
        talepDatamodel = new TalepDataModel(dataList);
        talepLoaded = true;
    }

    public void getDuyuruTabInfos() {
        List<Duyuru> dataList = ortakService.getAllDuyuruListByDaire(selected);
        duyuruDataModel = new DuyuruDataModel(dataList);
        ilanLoaded = true;
    }

    public void getBorcTabInfos() {
        Double toplemBorc = 0.0;
        Double toplamOdeme = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        List<DaireBorc> dataList = daireService.getBorcListByDaire(selected);
        for (DaireBorc db : dataList) {
            toplamOdeme += db.getBorc().getOdenenTutar() != null ? db.getBorc().getOdenenTutar() : 0.0;
            toplemBorc += db.getBorc().getBorc() != null ? db.getBorc().getBorc() : 0.0;
        }
        this.setToplamBorcTutar(df.format(toplemBorc));
        this.setToplamOdenenTutar(df.format(toplamOdeme));
        this.setToplamBakiyeTutar(df.format(toplamOdeme - toplemBorc));
        borcDataModel = new BorcDataModel(dataList);
        aidatLoaded = true;
    }

    public void getDaireBilgileriTabInfos() {
        List<KullaniciDaire> dataList = daireService.getKullaniciListByDaire(selected);
        kullaniciDaireDataModel = new KullaniciDaireDataModel(dataList);
        sakinLoaded = true;
    }

    public void getBelgelerTabInfos() {
        List<DaireBelge> belgeList = fileUploadService.getDaireBelgeList(selected);
        belgeDataModel = new DaireBelgeDataModel(belgeList);
        belgeLoaded = true;
    }

    public void getDaireListBySorguKriteri() {
        List<Daire> dataList = daireService.getListCriteriaForPaging(0, 500, sorguKriteri, sessionInfo);
        daireSorguSonucu = dataList;
        dataModel = new DaireDataModel(dataList);

        if (dataList == null || dataList.size() <= 0) {
            JsfUtil.addWarnMessage(message.getString("error.sonuc.yok"));
        }
    }

    public List<Kullanici> completePlayer(String query) {
        List<Kullanici> suggestions = ortakService.getKullaniciByName(query , sessionInfo);
        return suggestions;
    }

    public void setUserRolInfos() {
        if (!sessionInfo.isAdminMi() && selected == null) {
            sorguKriteri.setKullanici(sessionInfo.getKullanici());
            getDaireListBySorguKriteri();
            if (dataModel.getRowCount() >= 1) {
                selected = daireSorguSonucu.get(0);
                ortakService.createErisimLog(sessionInfo, sessionInfo.getKullanici(), LogTipi.getDaireGoruntuleObject(), label.getString("daire_kodu") + ":" + selected.getDaireKodu());
                getDaireBilgileriTabInfos();
            }
        }
    }

    public void yeniArizaTalebiEkleme() {
        talepService.arizaTalebiEkleme(selected, yeniArizaTalep, sessionInfo);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ARIZA, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ARIZA, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ariza_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        getTalepTabInfos();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('talepEklePopup').hide()");
    }

    public void yeniSikayetTalebiEkleme() {
        talepService.sikayetTalebiEkleme(selected, yeniSikayetTalebi, sessionInfo);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.SIKAYET, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.SIKAYET, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("sikayet_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        getTalepTabInfos();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('sikayetTalebiPopup').hide()");
    }

    public void yeniItirazTalebiEkleme() {
        talepService.itirazTalebiEkleme(selected, yeniItiraz, sessionInfo, selectedBorc);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.AIDAT_ITIRAZ, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.AIDAT_ITIRAZ, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("itiraz_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        getTalepTabInfos();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('itirazTalepEklePopup').hide()");
    }

    public void yeniBelgeTalebiEkleme() {
        talepService.belgeTalebiEkleme(selected, yeniBelgeTalebi, sessionInfo);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.BELGE_TALEBI, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.BELGE_TALEBI, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("belge_talebi_gonderme_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        JsfUtil.addSuccessMessage(message.getString("talep_ekleme_basarili"));
        getTalepTabInfos();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('belgeTalebiPopup').hide()");
    }

    public void yeniDuyuruEkle() {
        try {
            yeniDuyuru.setIlanMi(EvetHayir.getEvetObject());
            yeniDuyuru.setDaire(getSelected());
            yeniDuyuru.setKullanici(sessionInfo.getKullanici());
            yeniDuyuru.setTanitimZamani(new Date());
            yeniDuyuru.setDurum(Durum.getAktifObject());
            ortakService.yeniDuyuruEkle(sessionInfo, yeniDuyuru);
            ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ILAN_EKLEME, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(),BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ILAN_EKLEME, selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(), selected.getBlok().getAciklama() + " " + selected.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(),BilgilendirmeTipi.ENUM.Notification);
            getDuyuruTabInfos();
            JsfUtil.addSuccessMessage(message.getString("duyuru_ekleme_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('duyuruEklePopup').hide()");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage(e.toString());
        }
    }

    public void deleteDuyuru() {
        try {
            ortakService.deleteDuyuru(selectedDuyuru);
            getDuyuruTabInfos();
            JsfUtil.addSuccessMessage(message.getString("duyuru_silme_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void update(Daire daire) {
        try {
            daireService.update(daire);
            JsfUtil.addSuccessMessage("?slem basarili");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void delete(Daire daire) {
        try {
            daireService.update(daire);
            JsfUtil.addSuccessMessage("?slem basarili");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
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

    public void belgeUpload(FileUploadEvent event) {
        if (event.getFile().equals(null)) {
            JsfUtil.addSuccessMessage("file is null");
        }
        try {
            UploadedFile uploadedFile = event.getFile();
            fileUploadService.daireBelgeEkle(sessionInfo, selected, uploadedFile, yeniBelge);
            JsfUtil.addSuccessMessage(message.getString("belge_ekleme_basarili"));
            getBelgelerTabInfos();
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("error reading file " + e);
        }

    }

    public void deleteBelge() {
        try {
            fileUploadService.belgeSil(selectedDaireBelge);
            JsfUtil.addSuccessMessage(message.getString("belge_silme_basarili"));
            getBelgelerTabInfos();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void clearBelgeYuklemeObject() {
        yeniBelge = new Belge();
    }


    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void createPieModels() {
        createPieModel1();
        createPieModel2();
         finansLoaded = true;
    }

    public String doubleFormatter(Double deger) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(deger);
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        borcAlacakViewBeanList = finansalIslemlerService.getBorcAlacakDurumuForDaire(sessionInfo, selected, null);
        for (BorcAlacakViewBean bav : borcAlacakViewBeanList) {
            pieModel1.set((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")), bav.getTutar());
            bav.setAciklama((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")));
        }
    }

    private void createPieModel2() {
        pieModel2 = new PieChartModel();
        sonDonemborcAlacakViewBeanList = finansalIslemlerService.getBorcAlacakDurumuForDaire(sessionInfo, selected, DateUtils.getFirstDayOfTheMonthWithoutTime());
        for (BorcAlacakViewBean bav : sonDonemborcAlacakViewBeanList) {
            pieModel2.set((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")), bav.getTutar());
            bav.setAciklama((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")));
        }
    }

    public Double getBorcTutariForGenel() {
        return borcAlacakViewBeanList.get(0).getTutar() - borcAlacakViewBeanList.get(1).getTutar();
    }

    public Double getBorcTutariForCurrent() {
        return sonDonemborcAlacakViewBeanList.get(0).getTutar() - sonDonemborcAlacakViewBeanList.get(1).getTutar();
    }


    //kontroller
    public void cleanPage() {
        sorguKriteri.setBlok(null);
        sorguKriteri.setDaireNo(null);
        dataModel = null;
        borcDataModel = null;
        talepDatamodel = null;
        duyuruDataModel = null;
    }

    public void chectAidatItiraz(DaireBorc daireBorc) {
        if (daireBorc == null) {
            JsfUtil.addErrorMessage(message.getString("aida_itiraz_icin_borc_secilmeli"));
        } else {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('itirazTalepEklePopup').show()");
        }
    }

    //page navigations
    public String daireGoruntule() {
        cleanPage();
        getDaireBilgileriTabInfos();
        ortakService.createErisimLog(sessionInfo, sessionInfo.getKullanici(), LogTipi.getDaireGoruntuleObject(), label.getString("daire_kodu") + ":" + selected.getDaireKodu());
        return "daireGoruntule";
    }

    //getter and setters


    public IDaireService getDaireService() {
        return daireService;
    }

    public void setDaireService(IDaireService daireService) {
        this.daireService = daireService;
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

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public ITalepService getTalepService() {
        return talepService;
    }

    public void setTalepService(ITalepService talepService) {
        this.talepService = talepService;
    }

    public DaireSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(DaireSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public TalepSorguKriteri getTalepSorguKriteri() {
        return talepSorguKriteri;
    }

    public void setTalepSorguKriteri(TalepSorguKriteri talepSorguKriteri) {
        this.talepSorguKriteri = talepSorguKriteri;
    }

    public ArizaTalebi getYeniArizaTalep() {
        return yeniArizaTalep;
    }

    public void setYeniArizaTalep(ArizaTalebi yeniArizaTalep) {
        this.yeniArizaTalep = yeniArizaTalep;
    }

    public TalepDaire getSelectedTalep() {
        return selectedTalep;
    }

    public void setSelectedTalep(TalepDaire selectedTalep) {
        this.selectedTalep = selectedTalep;
    }

    public TalepDataModel getTalepDatamodel() {
        return talepDatamodel;
    }

    public void setTalepDatamodel(TalepDataModel talepDatamodel) {
        this.talepDatamodel = talepDatamodel;
    }

    public String getTalepIslem() {
        return talepIslem;
    }

    public void setTalepIslem(String talepIslem) {
        this.talepIslem = talepIslem;
    }

    public Daire getSelected() {
        return selected;
    }

    public void setSelected(Daire selected) {
        this.selected = selected;
    }

    public DaireDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DaireDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }

    public Duyuru getYeniDuyuru() {
        return yeniDuyuru;
    }

    public void setYeniDuyuru(Duyuru yeniDuyuru) {
        this.yeniDuyuru = yeniDuyuru;
    }

    public Duyuru getSelectedDuyuru() {
        return selectedDuyuru;
    }

    public void setSelectedDuyuru(Duyuru selectedDuyuru) {
        this.selectedDuyuru = selectedDuyuru;
    }

    public DuyuruDataModel getDuyuruDataModel() {
        return duyuruDataModel;
    }

    public void setDuyuruDataModel(DuyuruDataModel duyuruDataModel) {
        this.duyuruDataModel = duyuruDataModel;
    }

    public DaireBorc getSelectedBorc() {
        return selectedBorc;
    }

    public void setSelectedBorc(DaireBorc selectedBorc) {
        this.selectedBorc = selectedBorc;
    }

    public BorcDataModel getBorcDataModel() {
        return borcDataModel;
    }

    public void setBorcDataModel(BorcDataModel borcDataModel) {
        this.borcDataModel = borcDataModel;
    }

    public KullaniciDaireDataModel getKullaniciDaireDataModel() {
        return kullaniciDaireDataModel;
    }

    public void setKullaniciDaireDataModel(KullaniciDaireDataModel kullaniciDaireDataModel) {
        this.kullaniciDaireDataModel = kullaniciDaireDataModel;
    }

    public Kullanici getSelectedKullanici() {
        return selectedKullanici;
    }

    public void setSelectedKullanici(Kullanici selectedKullanici) {
        this.selectedKullanici = selectedKullanici;
    }

    public KullaniciDaire getSelectedKullaniciDaire() {
        return selectedKullaniciDaire;
    }

    public void setSelectedKullaniciDaire(KullaniciDaire selectedKullaniciDaire) {
        this.selectedKullaniciDaire = selectedKullaniciDaire;
    }

    public List<Kullanici> getKullaniciList() {
        return kullaniciList;
    }

    public void setKullaniciList(List<Kullanici> kullaniciList) {
        this.kullaniciList = kullaniciList;
    }

    public ItirazTalebi getYeniItiraz() {
        return yeniItiraz;
    }

    public void setYeniItiraz(ItirazTalebi yeniItiraz) {
        this.yeniItiraz = yeniItiraz;
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

    public DaireBelge getSelectedDaireBelge() {
        return selectedDaireBelge;
    }

    public void setSelectedDaireBelge(DaireBelge selectedDaireBelge) {
        this.selectedDaireBelge = selectedDaireBelge;
    }

    public DaireBelgeDataModel getBelgeDataModel() {
        return belgeDataModel;
    }

    public void setBelgeDataModel(DaireBelgeDataModel belgeDataModel) {
        this.belgeDataModel = belgeDataModel;
    }

    public boolean isIstamamlandiMi() {
        return istamamlandiMi;
    }

    public void setIstamamlandiMi(boolean istamamlandiMi) {
        this.istamamlandiMi = istamamlandiMi;
    }

    public SikayetTalebi getYeniSikayetTalebi() {
        return yeniSikayetTalebi;
    }

    public void setYeniSikayetTalebi(SikayetTalebi yeniSikayetTalebi) {
        this.yeniSikayetTalebi = yeniSikayetTalebi;
    }

    public String getToplamBorcTutar() {
        return toplamBorcTutar;
    }

    public void setToplamBorcTutar(String toplamBorcTutar) {
        this.toplamBorcTutar = toplamBorcTutar;
    }

    public String getToplamOdenenTutar() {
        return toplamOdenenTutar;
    }

    public void setToplamOdenenTutar(String toplamOdenenTutar) {
        this.toplamOdenenTutar = toplamOdenenTutar;
    }

    public String getToplamBakiyeTutar() {
        return toplamBakiyeTutar;
    }

    public void setToplamBakiyeTutar(String toplamBakiyeTutar) {
        this.toplamBakiyeTutar = toplamBakiyeTutar;
    }

    public BelgeTalebi getYeniBelgeTalebi() {
        return yeniBelgeTalebi;
    }

    public void setYeniBelgeTalebi(BelgeTalebi yeniBelgeTalebi) {
        this.yeniBelgeTalebi = yeniBelgeTalebi;
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

    public List<BorcAlacakViewBean> getSonDonemborcAlacakViewBeanList() {
        return sonDonemborcAlacakViewBeanList;
    }

    public void setSonDonemborcAlacakViewBeanList(List<BorcAlacakViewBean> sonDonemborcAlacakViewBeanList) {
        this.sonDonemborcAlacakViewBeanList = sonDonemborcAlacakViewBeanList;
    }

    public List<BorcAlacakViewBean> getBorcAlacakViewBeanList() {
        return borcAlacakViewBeanList;
    }

    public void setBorcAlacakViewBeanList(List<BorcAlacakViewBean> borcAlacakViewBeanList) {
        this.borcAlacakViewBeanList = borcAlacakViewBeanList;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public IFinansalIslemlerService getFinansalIslemlerService() {
        return finansalIslemlerService;
    }

    public void setFinansalIslemlerService(IFinansalIslemlerService finansalIslemlerService) {
        this.finansalIslemlerService = finansalIslemlerService;
    }

    public List<Daire> getDaireSorguSonucu() {
        return daireSorguSonucu;
    }

    public void setDaireSorguSonucu(List<Daire> daireSorguSonucu) {
        this.daireSorguSonucu = daireSorguSonucu;
    }

    public boolean isAidatLoaded() {
        return aidatLoaded;
    }

    public void setAidatLoaded(boolean aidatLoaded) {
        this.aidatLoaded = aidatLoaded;
    }

    public boolean isFinansLoaded() {
        return finansLoaded;
    }

    public void setFinansLoaded(boolean finansLoaded) {
        this.finansLoaded = finansLoaded;
    }

    public boolean isBelgeLoaded() {
        return belgeLoaded;
    }

    public void setBelgeLoaded(boolean belgeLoaded) {
        this.belgeLoaded = belgeLoaded;
    }

    public boolean isTalepLoaded() {
        return talepLoaded;
    }

    public void setTalepLoaded(boolean talepLoaded) {
        this.talepLoaded = talepLoaded;
    }

    public boolean isIlanLoaded() {
        return ilanLoaded;
    }

    public void setIlanLoaded(boolean ilanLoaded) {
        this.ilanLoaded = ilanLoaded;
    }

    public boolean isSakinLoaded() {
        return sakinLoaded;
    }

    public void setSakinLoaded(boolean sakinLoaded) {
        this.sakinLoaded = sakinLoaded;
    }
}