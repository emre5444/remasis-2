package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.AnketDataModel;
import com.ronin.managed.bean.lazydatamodel.AnketKullaniciDataModel;
import com.ronin.managed.bean.lazydatamodel.AnketSecenekDataModel;
import com.ronin.managed.bean.lazydatamodel.KullaniciDaireDataModel;
import com.ronin.model.Anket;
import com.ronin.model.AnketKullanici;
import com.ronin.model.AnketSecim;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.BilgilendirmeTipi;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.AnketSorguKriteri;
import com.ronin.model.sorguSonucu.AnketSonucViewBean;
import com.ronin.model.sorguSonucu.BorcAlacakViewBean;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "anketMB")
@ViewScoped
public class AnketMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    private AnketSorguKriteri sorguKriteri = new AnketSorguKriteri();
    private Anket selectedAnket;
    private AnketSecim selectedAnketSecim;
    private Anket yeniAnket = new Anket();
    private List<AnketSecim> anketSecimList;

    //kullanici anket , anket secenek islemleri
    private AnketSecenekDataModel anketSecenekDataModel;
    private AnketDataModel dataModel;
    private AnketKullaniciDataModel anketKullaniciDataModel;
    private List<AnketKullanici> anketKullaniciList;

    private List<AnketSonucViewBean> anketSonucViewBeanList;

    //combolar
    private List<IAbstractEntity> anketAktifPasifList;

    public boolean guncellemeMi = false;


    //charts
    private PieChartModel pieModelAnket;

    @PostConstruct
    public void init() {
        prepareCombos();
    }

    public void getAnketListBySorguKriteri() {
        List<Anket> dataList = ortakService.getAnketListBySorguSonucu(0, 100, sorguKriteri, sessionInfo);
        dataModel = new AnketDataModel(dataList);
    }

    public void addTempAnketSecimToAnket() {
        anketSecimList = ortakService.addSecenekToAnket(sessionInfo, anketSecimList, yeniAnket, yeniAnket.getYeniSecenek());
        anketSecenekDataModel = new AnketSecenekDataModel(anketSecimList);
        yeniAnket.setYeniSecenek("");
    }

    public void deleteTempSecimToAnket() {
        anketSecimList = ortakService.deleteTempSecimToAnket(anketSecimList, selectedAnketSecim);
        anketSecenekDataModel = new AnketSecenekDataModel(anketSecimList);
    }

    public void prepateAnketSecimList() {
        anketSecimList = ortakService.getAnketSecimListFromAnket(selectedAnket);
        anketSecenekDataModel = new AnketSecenekDataModel(anketSecimList);
    }

    public void prepareAnketKullaniciList() {
        anketKullaniciList = ortakService.getKullaniciAnketListByAnket(selectedAnket);
        anketKullaniciDataModel = new AnketKullaniciDataModel(anketKullaniciList);
    }

    private void createPieModelForAnket() {
        pieModelAnket = new PieChartModel();
        anketSonucViewBeanList = ortakService.getAnketSonucDurum(sessionInfo, selectedAnket);
        for (AnketSonucViewBean bav : anketSonucViewBeanList) {
            pieModelAnket.set(bav.getSecim(), bav.getTutar());
        }
    }

    public void clearAnketInfo() {
        anketKullaniciDataModel = null;
        anketSecenekDataModel = null;
        anketSecimList = null;
        anketKullaniciList = null;
        yeniAnket = new Anket();
        guncellemeMi = false;
    }

    public EvetHayir isAnketeKtinildiMi(Anket anket) {
        EvetHayir evetHayir = ortakService.isAnketeKatinildiMi(sessionInfo, anket);
        return (EvetHayir) ortakService.getEntityByClass(EvetHayir.class, evetHayir.getId());
    }

    public void prepareAnketDetailInfo() {
        createPieModelForAnket();
        prepareAnketKullaniciList();
        prepateAnketSecimList();
    }

    public void prepareAnketDetailForKatilim() {
        prepateAnketSecimList();
    }

    public void prepareAnketDetailInfoForGuncelleme() {
        guncellemeMi = true;
        yeniAnket = selectedAnket;
        prepateAnketSecimList();
        prepareAnketKullaniciList();
    }

    public void anketeKatilimEkle() {
        try {
            ortakService.anketeKatilimEkleme(sessionInfo, selectedAnket, yeniAnket.getSelectedAnketSecim(), yeniAnket.getOyAciklama());
            JsfUtil.addSuccessMessage(message.getString("anket_oy_kullanma_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('anketeKatilimEklemePopup').hide()");
            getAnketListBySorguKriteri();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void prepareCombos() {
        anketAktifPasifList = ortakService.getListByNamedQuery("EvetHayir.findAll");
    }

    public void deleteAnket() {
        try {
            ortakService.deleteAnket(selectedAnket);
            JsfUtil.addSuccessMessage(message.getString("anket_silme_basarili"));
            getAnketListBySorguKriteri();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void anketEkleDecision() {
        if (anketSecimList == null || anketSecimList.size() <= 1) {
            JsfUtil.addErrorMessage(message.getString("anket_senecek_yok"));
        } else {
            if (guncellemeMi) {
                yeniAnketGuncelle();
            } else {
                yeniAnketEkle();
            }
        }
    }

    public void yeniAnketGuncelle() {
        try {
            ortakService.anketGuncelle(yeniAnket, anketSecimList);
            getAnketListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("anket_guncelleme_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('anketEklemePopup').hide()");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public void yeniAnketEkle() {
        try {
            ortakService.yeniAnketEkle(sessionInfo, yeniAnket, anketSecimList);
            ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ANKET, yeniAnket.getAnketKonusu() + " " + message.getString("anket_bildirim_mesaj"), yeniAnket.getAnketKonusu() + " " + message.getString("anket_bildirim_mesaj"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ANKET, yeniAnket.getAnketKonusu() + " " + message.getString("anket_bildirim_mesaj"), yeniAnket.getAnketKonusu() + " " + message.getString("anket_bildirim_mesaj"), BilgilendirmeTipi.ENUM.Notification);
            getAnketListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("anket_ekleme_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('anketEklemePopup').hide()");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }


    public List<IAbstractEntity> getAnketAktifPasifList() {
        return anketAktifPasifList;
    }

    public void setAnketAktifPasifList(List<IAbstractEntity> anketAktifPasifList) {
        this.anketAktifPasifList = anketAktifPasifList;
    }

    public AnketKullaniciDataModel getAnketKullaniciDataModel() {
        return anketKullaniciDataModel;
    }

    public void setAnketKullaniciDataModel(AnketKullaniciDataModel anketKullaniciDataModel) {
        this.anketKullaniciDataModel = anketKullaniciDataModel;
    }

    public AnketDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(AnketDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public AnketSecenekDataModel getAnketSecenekDataModel() {
        return anketSecenekDataModel;
    }

    public void setAnketSecenekDataModel(AnketSecenekDataModel anketSecenekDataModel) {
        this.anketSecenekDataModel = anketSecenekDataModel;
    }

    public List<AnketSecim> getAnketSecimList() {
        return anketSecimList;
    }

    public void setAnketSecimList(List<AnketSecim> anketSecimList) {
        this.anketSecimList = anketSecimList;
    }

    public Anket getYeniAnket() {
        return yeniAnket;
    }

    public void setYeniAnket(Anket yeniAnket) {
        this.yeniAnket = yeniAnket;
    }

    public Anket getSelectedAnket() {
        return selectedAnket;
    }

    public void setSelectedAnket(Anket selectedAnket) {
        this.selectedAnket = selectedAnket;
    }

    public AnketSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(AnketSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public ResourceBundle getLabel() {
        return label;
    }

    public void setLabel(ResourceBundle label) {
        this.label = label;
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
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

    public AnketSecim getSelectedAnketSecim() {
        return selectedAnketSecim;
    }

    public void setSelectedAnketSecim(AnketSecim selectedAnketSecim) {
        this.selectedAnketSecim = selectedAnketSecim;
    }

    public List<AnketKullanici> getAnketKullaniciList() {
        return anketKullaniciList;
    }

    public void setAnketKullaniciList(List<AnketKullanici> anketKullaniciList) {
        this.anketKullaniciList = anketKullaniciList;
    }

    public PieChartModel getPieModelAnket() {
        return pieModelAnket;
    }

    public void setPieModelAnket(PieChartModel pieModelAnket) {
        this.pieModelAnket = pieModelAnket;
    }

    public List<AnketSonucViewBean> getAnketSonucViewBeanList() {
        return anketSonucViewBeanList;
    }

    public void setAnketSonucViewBeanList(List<AnketSonucViewBean> anketSonucViewBeanList) {
        this.anketSonucViewBeanList = anketSonucViewBeanList;
    }
}
