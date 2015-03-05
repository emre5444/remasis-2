package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.AnketDataModel;
import com.ronin.managed.bean.lazydatamodel.AnketKullaniciDataModel;
import com.ronin.managed.bean.lazydatamodel.BelgeDataModel;
import com.ronin.managed.bean.lazydatamodel.DuyuruDataModel;
import com.ronin.model.Anket;
import com.ronin.model.AnketKullanici;
import com.ronin.model.AnketSecim;
import com.ronin.model.Duyuru;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.*;
import com.ronin.model.kriter.AnketSorguKriteri;
import com.ronin.model.sorguSonucu.AnketSonucViewBean;
import com.ronin.model.sorguSonucu.BorcAlacakViewBean;
import com.ronin.service.IFileUploadService;
import com.ronin.service.IFinansalIslemlerService;
import com.ronin.utils.DateUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "anasayfaMB")
@ViewScoped
public class AnasayfaMB implements Serializable {

    public static Logger logger = Logger.getLogger(AnasayfaMB.class);

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{fileUploadService}")
    private IFileUploadService fileUploadService;

    @ManagedProperty("#{finansalIslemlerService}")
    private IFinansalIslemlerService finansalIslemlerService;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    private String eskiSifre;

    private String yeniSifre;

    private Duyuru selected;

    private Belge selectedBelge;

    private DuyuruDataModel duyuruDataModel;

    private DuyuruDataModel ilanDataModel;

    private BelgeDataModel belgeDataModel;

    private Duyuru yeniDuyuru = new Duyuru();
    private IletisimBilgileri yeniIletisimBilgisi = new IletisimBilgileri();

    private List<IletisimBilgileri> iletisimBilgiList;

    private DefaultStreamedContent download;

    private Belge yeniBelge = new Belge();



    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private PieChartModel pieModelAnket;

    private boolean skip;
    private List<BorcAlacakViewBean> borcAlacakViewBeanList;
    private List<BorcAlacakViewBean> sonDonemborcAlacakViewBeanList;

    //anket islemleri
    private AnketDataModel anketDataModel;
    private List<Anket> anketList;
    private Anket selectedAnket;
    private AnketSecim selectedAnketSecim;
    private List<AnketSecim> anketSecimList;
    private AnketKullaniciDataModel anketKullaniciDataModel;
    private List<AnketKullanici> anketKullaniciList;
    private List<AnketSonucViewBean> anketSonucViewBeanList;
    private Anket yeniAnket = new Anket();

    //combolar
    private List<IAbstractEntity> belgeTipiList;
    private List<IAbstractEntity> anketAktifPasifList;

    private boolean openDialog;

    //loading booleans

    boolean duyuruLoaded = false;
    boolean ilanLoaded = false;
    boolean belgeLoaded = false;
    boolean anketLoaded = false;
    boolean iletisimLoaded = false;
    boolean finansLoaded = false;



    @PostConstruct
    public void init() {
        prepareDummyLoadObjects();
    }

    public void createPieModels() {
        if (sessionInfo.isAdminMi()) {
            createPieModel1();
            createPieModel2();
            finansLoaded = true;

        }
    }

    public void prepareAnketDetailInfo() {
        createPieModelForAnket();
        prepareAnketKullaniciList();
        prepateAnketSecimList();
    }

    public void prepareAnketDetailForKatilim() {
        prepateAnketSecimList();
    }

    public void prepateAnketSecimList() {
        anketSecimList = getOrtakService().getAnketSecimListFromAnket(selectedAnket);
    }

    public void prepareAnketKullaniciList() {
        anketKullaniciList = getOrtakService().getKullaniciAnketListByAnket(selectedAnket);
        anketKullaniciDataModel = new AnketKullaniciDataModel(anketKullaniciList);
    }

    public void anketeKatilimEkle() {
        try {
            getOrtakService().anketeKatilimEkleme(sessionInfo, selectedAnket, yeniAnket.getSelectedAnketSecim(), yeniAnket.getOyAciklama());
            JsfUtil.addSuccessMessage(message.getString("anket_oy_kullanma_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('anketeKatilimEklemePopup').hide()");
            getAnketListBySorguKriteri();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void getAnketListBySorguKriteri() {
        anketList = null;
        AnketSorguKriteri sorguKriteri = new AnketSorguKriteri();
        sorguKriteri.setAnketAktifMi((EvetHayir) getOrtakService().getEntityByClass(EvetHayir.class, EvetHayir.getEvetObject().getId()));
        anketList = getOrtakService().getAnketListBySorguSonucu(0, 100, sorguKriteri, sessionInfo);
        for (Anket anket : anketList) {
            anket.setAnketeKatinildiMi(isAnketeKtinildiMi(anket));
        }
        anketDataModel = new AnketDataModel(anketList);
        openDialog = false;
        anketLoaded = true;
        if (anketList != null && anketList.size() > 0) {
            for (Anket anket : anketList) {
                if (anket.getAnketeKatinildiMi().isHayirMi()) {
                    openDialog = true;
                    setSelectedAnket(anket);
                    prepareAnketDetailForKatilim();
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.execute("PF('anketeKatilimEklemePopup').show()");
                    break;
                }
            }
        }

    }

    private void createPieModelForAnket() {
        pieModelAnket = new PieChartModel();
        anketSonucViewBeanList = getOrtakService().getAnketSonucDurum(sessionInfo, selectedAnket);
        for (AnketSonucViewBean bav : anketSonucViewBeanList) {
            pieModelAnket.set(bav.getSecim(), bav.getTutar());
        }
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        borcAlacakViewBeanList = finansalIslemlerService.getBorcAlacakDurumu(sessionInfo, null);
        for (BorcAlacakViewBean bav : borcAlacakViewBeanList) {
            if (!sessionInfo.isAdminMi()) {
                pieModel1.set(bav.getDaire().getDaireKodu() + " " + (bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")), bav.getTutar());
                bav.setAciklama(bav.getDaire().getDaireKodu() + " " + (bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")));
            } else {
                pieModel1.set((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")), bav.getTutar());
                bav.setAciklama((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")));

            }
        }
    }

    private void createPieModel2() {
        pieModel2 = new PieChartModel();
        sonDonemborcAlacakViewBeanList = finansalIslemlerService.getBorcAlacakDurumu(sessionInfo, DateUtils.getFirstDayOfTheMonthWithoutTime());
        for (BorcAlacakViewBean bav : sonDonemborcAlacakViewBeanList) {
            if (!sessionInfo.isAdminMi()) {
                pieModel2.set(bav.getDaire().getDaireKodu() + " " + (bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")), bav.getTutar());
                bav.setAciklama(bav.getDaire().getDaireKodu() + " " + (bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")));
            } else {
                pieModel2.set((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")), bav.getTutar());
                bav.setAciklama((bav.getBorcTipi().isBorclumu() ? label.getString("label_toplam_borc") : label.getString("label_toplam_odenen")));

            }
        }
    }

    public Double getBorcTutariForGenel() {
        if (borcAlacakViewBeanList.isEmpty())
            return 0.0;
        return borcAlacakViewBeanList.get(0).getTutar() - borcAlacakViewBeanList.get(1).getTutar();
    }

    public Double getBorcTutariForCurrent() {
        if (sonDonemborcAlacakViewBeanList.isEmpty())
            return 0.0;
        return sonDonemborcAlacakViewBeanList.get(0).getTutar() - sonDonemborcAlacakViewBeanList.get(1).getTutar();
    }

    public EvetHayir isAnketeKtinildiMi(Anket anket) {
        EvetHayir evetHayir = getOrtakService().isAnketeKatinildiMi(sessionInfo, anket);
        return (EvetHayir) getOrtakService().getEntityByClass(EvetHayir.class, evetHayir.getId());
    }

    public DefaultStreamedContent getDownload() {
        try {
            InputStream input = selectedBelge.getContent().getBinaryStream();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            download = new DefaultStreamedContent(input, externalContext.getMimeType(selectedBelge.getDataName()), selectedBelge.getDataName());
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
        InputStream input = selectedBelge.getContent().getBinaryStream();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(selectedBelge.getDataName()), selectedBelge.getDataName()));
    }

    public void belgeUpload(FileUploadEvent event) {
        if (event.getFile().equals(null)) {
            JsfUtil.addSuccessMessage("file is null");
        }
        try {
            UploadedFile uploadedFile = event.getFile();
            fileUploadService.belgeEkle(sessionInfo, uploadedFile, yeniBelge);
            JsfUtil.addSuccessMessage(message.getString("belge_ekleme_basarili"));
            getBelgeData();
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("error reading file " + e);
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


    public void updateKullanici() {
        Kullanici selectedKullanici = sessionInfo.getKullanici();
        if (selectedKullanici.getPassword().equals(getOrtakService().getMD5String(eskiSifre))) {
            try {
                selectedKullanici.setPassword(yeniSifre);
                kullaniciService.updateKullaniciWithPasword(selectedKullanici);
                JsfUtil.addSuccessMessage(message.getString("sifre_guncelleme_basarili"));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PF('sifreDegistirmePopup').hide()");
            } catch (Exception e) {
                logger.error(e.getStackTrace());
                JsfUtil.addSuccessMessage(e.toString());
            }
        } else {
            JsfUtil.addErrorMessage(message.getString("error_sifreler_farkli_olamaz"));
        }
    }

    public void prepareDummyLoadObjects() {
        belgeTipiList = ortakService.getListByNamedQuery("BelgeTipi.findAll");
        anketAktifPasifList = ortakService.getListByNamedQuery("EvetHayir.findAll");
        duyuruLoaded = false;
        ilanLoaded = false;
        belgeLoaded = false;
        anketLoaded = false;
        iletisimLoaded = false;
        finansLoaded = false;
    }

    public void getDuyuruData() {
        List<Duyuru> dataList = getOrtakService().getAllDuyuruList(sessionInfo);
        duyuruDataModel = new DuyuruDataModel(dataList);
        duyuruLoaded = true;
    }

    public void getIlanData() {
        List<Duyuru> dataListIlanlar = getOrtakService().getAllIlanList(sessionInfo);
        ilanDataModel = new DuyuruDataModel(dataListIlanlar);
        ilanLoaded = true;
    }

    public void getBelgeData() {
        List<Belge> belgeList = fileUploadService.getAdminBelgeList(sessionInfo);
        belgeDataModel = new BelgeDataModel(belgeList);
        belgeLoaded = true;
    }

    public void prepareIletisimBilgi() {
        yeniIletisimBilgisi = new IletisimBilgileri();
        iletisimBilgiList = getOrtakService().getAllIletisimBilgileriBySirket(sessionInfo);
        iletisimLoaded = true;
    }

    public void getAnasayfaInfos() {
        getDuyuruData();
        getIlanData();
        getBelgeData();
        prepareIletisimBilgi();
        getAnketListBySorguKriteri();
        createPieModels();
    }



    public void deleteDuyuru() {
        try {
            getOrtakService().deleteDuyuru(selected);
            JsfUtil.addSuccessMessage(message.getString("duyuru_silme_basarili"));
            getDuyuruData();
            getIlanData();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void deleteBelge() {
        try {
            getOrtakService().deleteBelge(selectedBelge);
            JsfUtil.addSuccessMessage(message.getString("belge_silme_basarili"));
            getBelgeData();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void yeniDuyuruEkle() {
        try {
            yeniDuyuru.setIlanMi(EvetHayir.getHayirObject());
            yeniDuyuru.setKullanici(sessionInfo.getKullanici());
            yeniDuyuru.setTanitimZamani(new Date());
            yeniDuyuru.setDurum(Durum.getAktifObject());
            getOrtakService().yeniDuyuruEkle(sessionInfo, yeniDuyuru);
            getOrtakService().bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.DUYURU, yeniDuyuru.getAciklama(), yeniDuyuru.getKisaAciklama(), BilgilendirmeTipi.ENUM.Email);
            getOrtakService().bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.DUYURU, yeniDuyuru.getAciklama(), yeniDuyuru.getKisaAciklama(), BilgilendirmeTipi.ENUM.Notification);
            JsfUtil.addSuccessMessage(message.getString("duyuru_ekleme_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('duyuruEklePopup').hide()");
            getDuyuruData();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }


    public void update(Duyuru duyuru) {
        try {
            getOrtakService().update(duyuru);
            getDuyuruData();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
        JsfUtil.addSuccessMessage("?slem basarili");
    }

    public void delete(Duyuru duyuru) {
        try {
            getOrtakService().update(duyuru);
            getDuyuruData();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
        JsfUtil.addSuccessMessage("?slem basarili");
    }

    public void onIletisimBilgileriRowEdit(RowEditEvent event) {
        IletisimBilgileri selectedIletisimBilgileri = (IletisimBilgileri) event.getObject();
        getOrtakService().updateIletisimBilgileri(selectedIletisimBilgileri);
        prepareIletisimBilgi();
        JsfUtil.addSuccessMessage(message.getString("iletisim_guncelleme_basarili"));
    }

    public void onIletisimBilgileriRowCancel(RowEditEvent event) {
        IletisimBilgileri selectedIletisimBilgileri = (IletisimBilgileri) event.getObject();
        getOrtakService().deleteIletisimBilgisi(selectedIletisimBilgileri);
        prepareIletisimBilgi();
        JsfUtil.addSuccessMessage(message.getString("iletisim_silme_basarili"));
    }

    public void iletisimBilgisiEkle() {
        getOrtakService().iletisimBilgisiEkle(sessionInfo, yeniIletisimBilgisi);
        prepareIletisimBilgi();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('iletisimBilgisiEklePopup').hide()");
        JsfUtil.addSuccessMessage(message.getString("iletisim_ekleme_basarili"));
    }

    public String duyuruGoruntule() {
        return "duyuruGoruntule";
    }

    public String doubleFormatter(Double deger) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(deger);
    }


    public DuyuruDataModel getDuyuruDataModel() {
        return duyuruDataModel;
    }

    public void setDuyuruDataModel(DuyuruDataModel duyuruDataModel) {
        this.duyuruDataModel = duyuruDataModel;
    }

    public Duyuru getSelected() {
        return selected;
    }

    public void setSelected(Duyuru selected) {
        this.selected = selected;
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public String getEskiSifre() {
        return eskiSifre;
    }

    public void setEskiSifre(String eskiSifre) {
        this.eskiSifre = eskiSifre;
    }

    public String getYeniSifre() {
        return yeniSifre;
    }

    public void setYeniSifre(String yeniSifre) {
        this.yeniSifre = yeniSifre;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Duyuru getYeniDuyuru() {
        return yeniDuyuru;
    }

    public void setYeniDuyuru(Duyuru yeniDuyuru) {
        this.yeniDuyuru = yeniDuyuru;
    }

    public DuyuruDataModel getIlanDataModel() {
        return ilanDataModel;
    }

    public void setIlanDataModel(DuyuruDataModel ilanDataModel) {
        this.ilanDataModel = ilanDataModel;
    }

    public List<IletisimBilgileri> getIletisimBilgiList() {
        return iletisimBilgiList;
    }

    public void setIletisimBilgiList(List<IletisimBilgileri> iletisimBilgiList) {
        this.iletisimBilgiList = iletisimBilgiList;
    }

    public IFileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public void setFileUploadService(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    public BelgeDataModel getBelgeDataModel() {
        return belgeDataModel;
    }

    public void setBelgeDataModel(BelgeDataModel belgeDataModel) {
        this.belgeDataModel = belgeDataModel;
    }

    public Belge getSelectedBelge() {
        return selectedBelge;
    }

    public void setSelectedBelge(Belge selectedBelge) {
        this.selectedBelge = selectedBelge;
    }

    public Belge getYeniBelge() {
        return yeniBelge;
    }

    public void setYeniBelge(Belge yeniBelge) {
        this.yeniBelge = yeniBelge;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public List<BorcAlacakViewBean> getBorcAlacakViewBeanList() {
        return borcAlacakViewBeanList;
    }

    public void setBorcAlacakViewBeanList(List<BorcAlacakViewBean> borcAlacakViewBeanList) {
        this.borcAlacakViewBeanList = borcAlacakViewBeanList;
    }

    public IFinansalIslemlerService getFinansalIslemlerService() {
        return finansalIslemlerService;
    }

    public void setFinansalIslemlerService(IFinansalIslemlerService finansalIslemlerService) {
        this.finansalIslemlerService = finansalIslemlerService;
    }

    public ResourceBundle getLabel() {
        return label;
    }

    public void setLabel(ResourceBundle label) {
        this.label = label;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }

    public List<BorcAlacakViewBean> getSonDonemborcAlacakViewBeanList() {
        return sonDonemborcAlacakViewBeanList;
    }

    public void setSonDonemborcAlacakViewBeanList(List<BorcAlacakViewBean> sonDonemborcAlacakViewBeanList) {
        this.sonDonemborcAlacakViewBeanList = sonDonemborcAlacakViewBeanList;
    }

    public AnketDataModel getAnketDataModel() {
        return anketDataModel;
    }

    public void setAnketDataModel(AnketDataModel anketDataModel) {
        this.anketDataModel = anketDataModel;
    }

    public List<Anket> getAnketList() {
        return anketList;
    }

    public void setAnketList(List<Anket> anketList) {
        this.anketList = anketList;
    }

    public Anket getSelectedAnket() {
        return selectedAnket;
    }

    public void setSelectedAnket(Anket selectedAnket) {
        this.selectedAnket = selectedAnket;
    }

    public List<AnketSonucViewBean> getAnketSonucViewBeanList() {
        return anketSonucViewBeanList;
    }

    public void setAnketSonucViewBeanList(List<AnketSonucViewBean> anketSonucViewBeanList) {
        this.anketSonucViewBeanList = anketSonucViewBeanList;
    }

    public List<AnketKullanici> getAnketKullaniciList() {
        return anketKullaniciList;
    }

    public void setAnketKullaniciList(List<AnketKullanici> anketKullaniciList) {
        this.anketKullaniciList = anketKullaniciList;
    }

    public AnketKullaniciDataModel getAnketKullaniciDataModel() {
        return anketKullaniciDataModel;
    }

    public void setAnketKullaniciDataModel(AnketKullaniciDataModel anketKullaniciDataModel) {
        this.anketKullaniciDataModel = anketKullaniciDataModel;
    }

    public List<AnketSecim> getAnketSecimList() {
        return anketSecimList;
    }

    public void setAnketSecimList(List<AnketSecim> anketSecimList) {
        this.anketSecimList = anketSecimList;
    }

    public AnketSecim getSelectedAnketSecim() {
        return selectedAnketSecim;
    }

    public void setSelectedAnketSecim(AnketSecim selectedAnketSecim) {
        this.selectedAnketSecim = selectedAnketSecim;
    }

    public PieChartModel getPieModelAnket() {
        return pieModelAnket;
    }

    public void setPieModelAnket(PieChartModel pieModelAnket) {
        this.pieModelAnket = pieModelAnket;
    }

    public Anket getYeniAnket() {
        return yeniAnket;
    }

    public void setYeniAnket(Anket yeniAnket) {
        this.yeniAnket = yeniAnket;
    }

    public boolean isOpenDialog() {
        return openDialog;
    }

    public void setOpenDialog(boolean openDialog) {
        this.openDialog = openDialog;
    }

    public IletisimBilgileri getYeniIletisimBilgisi() {
        return yeniIletisimBilgisi;
    }

    public void setYeniIletisimBilgisi(IletisimBilgileri yeniIletisimBilgisi) {
        this.yeniIletisimBilgisi = yeniIletisimBilgisi;
    }

    public boolean isFinansLoaded() {
        return finansLoaded;
    }

    public void setFinansLoaded(boolean finansLoaded) {
        this.finansLoaded = finansLoaded;
    }

    public boolean isIletisimLoaded() {
        return iletisimLoaded;
    }

    public void setIletisimLoaded(boolean iletisimLoaded) {
        this.iletisimLoaded = iletisimLoaded;
    }

    public boolean isAnketLoaded() {
        return anketLoaded;
    }

    public void setAnketLoaded(boolean anketLoaded) {
        this.anketLoaded = anketLoaded;
    }

    public boolean isBelgeLoaded() {
        return belgeLoaded;
    }

    public void setBelgeLoaded(boolean belgeLoaded) {
        this.belgeLoaded = belgeLoaded;
    }

    public boolean isIlanLoaded() {
        return ilanLoaded;
    }

    public void setIlanLoaded(boolean ilanLoaded) {
        this.ilanLoaded = ilanLoaded;
    }

    public boolean isDuyuruLoaded() {
        return duyuruLoaded;
    }

    public void setDuyuruLoaded(boolean duyuruLoaded) {
        this.duyuruLoaded = duyuruLoaded;
    }

    public List<IAbstractEntity> getAnketAktifPasifList() {
        return anketAktifPasifList;
    }

    public void setAnketAktifPasifList(List<IAbstractEntity> anketAktifPasifList) {
        this.anketAktifPasifList = anketAktifPasifList;
    }

    public List<IAbstractEntity> getBelgeTipiList() {
        return belgeTipiList;
    }

    public void setBelgeTipiList(List<IAbstractEntity> belgeTipiList) {
        this.belgeTipiList = belgeTipiList;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }
}
