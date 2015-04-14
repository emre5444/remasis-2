package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DuyuruDataModel;
import com.ronin.model.Daire;
import com.ronin.model.Duyuru;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.BilgilendirmeTipi;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "duyuruIslemleriMB")
@ViewScoped
public class DuyuruIslemleriMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    private DuyuruSorguKriteri sorguKriteri = new DuyuruSorguKriteri();

    private Duyuru selected;

    private DuyuruDataModel dataModel;

    private Duyuru yeniDuyuru = new Duyuru();

    private Daire selectedDaire;


    @PostConstruct
    public void init() {
        getFlushObjects();
    }

    public void getFlushObjects() {
        selected = (Duyuru) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDuyuruObject");
        selectedDaire = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaire");
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireObject", selectedDaire);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }


    public void getDuyuruListBySorguKriteri() {
        List<Duyuru> dataList = ortakService.getDuyuruListBySorguSonucu(0, 100, sorguKriteri, sessionInfo);
        dataModel = new DuyuruDataModel(dataList);
    }

    public void deleteDuyuru(Duyuru selectedDuyuru) {
        try {
            setSelected(selectedDuyuru);
            ortakService.deleteDuyuru(selected);
            JsfUtil.addSuccessMessage(message.getString("duyuru_silme_basarili"));
            getDuyuruListBySorguKriteri();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

   public String duyuruKaydiYarat() {
        if (selectedDaire != null) {
            return yeniIlanEkle();
        } else {
            return yeniDuyuruEkle();
        }
    }

   public String yeniDuyuruEkle() {
        yeniDuyuru.setIlanMi(EvetHayir.getHayirObject());
        yeniDuyuru.setKullanici(sessionInfo.getKullanici());
        yeniDuyuru.setTanitimZamani(new Date());
        yeniDuyuru.setDurum(Durum.getAktifObject());
        ortakService.yeniDuyuruEkle(sessionInfo, yeniDuyuru);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.DUYURU, yeniDuyuru.getAciklama(), yeniDuyuru.getKisaAciklama(), BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.DUYURU, yeniDuyuru.getAciklama(), yeniDuyuru.getKisaAciklama(), BilgilendirmeTipi.ENUM.Notification);
        JsfUtil.addSuccessMessage(message.getString("duyuru_ekleme_basarili"));
        return geriDon();
    }

    public String yeniIlanEkle() {
        yeniDuyuru.setIlanMi(EvetHayir.getEvetObject());
        yeniDuyuru.setDaire(getSelectedDaire());
        yeniDuyuru.setKullanici(sessionInfo.getKullanici());
        yeniDuyuru.setTanitimZamani(new Date());
        yeniDuyuru.setDurum(Durum.getAktifObject());
        ortakService.yeniDuyuruEkle(sessionInfo, yeniDuyuru);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ILAN_EKLEME, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(), BilgilendirmeTipi.ENUM.Email);
        ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.ILAN_EKLEME, selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(), selectedDaire.getBlok().getAciklama() + " " + selectedDaire.getDaireNo() + message.getString("ilan_ekleme_bildirim") + yeniDuyuru.getAciklama(), BilgilendirmeTipi.ENUM.Notification);
        JsfUtil.addSuccessMessage(message.getString("duyuru_ekleme_basarili"));
        return geriDon();
    }


    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
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

    public DuyuruSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(DuyuruSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public Duyuru getSelected() {
        return selected;
    }

    public void setSelected(Duyuru selected) {
        this.selected = selected;
    }

    public DuyuruDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DuyuruDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Duyuru getYeniDuyuru() {
        return yeniDuyuru;
    }

    public void setYeniDuyuru(Duyuru yeniDuyuru) {
        this.yeniDuyuru = yeniDuyuru;
    }

    public Daire getSelectedDaire() {
        return selectedDaire;
    }

    public void setSelectedDaire(Daire selectedDaire) {
        this.selectedDaire = selectedDaire;
    }
}
