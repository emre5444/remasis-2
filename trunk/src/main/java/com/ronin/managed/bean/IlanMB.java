package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DuyuruDataModel;
import com.ronin.model.Duyuru;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.BilgilendirmeTipi;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import com.ronin.model.kriter.IlanSorguKriteri;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "ilanMB")
@ViewScoped
public class IlanMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    private IlanSorguKriteri sorguKriteri = new IlanSorguKriteri();

    private Duyuru selected;

    private DuyuruDataModel dataModel;

    private Duyuru yeniDuyuru = new Duyuru();

    //combolar
    private List<IAbstractEntity> blokList;


    @PostConstruct
    public void init() {
        prepareCombos();
        getIlanListBySorguKriteri();
    }

    public void getIlanListBySorguKriteri() {
        List<Duyuru> dataList = ortakService.getIlanListBySorguSonucu(0, 100, sorguKriteri, sessionInfo);
        dataModel = new DuyuruDataModel(dataList);
    }

    public void prepareCombos() {
        dataModel = null;
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket",sessionInfo);
    }

    public void deleteDuyuru(Duyuru selectedDuyuru) {
        try {
            setSelected(selectedDuyuru);
            ortakService.deleteDuyuru(selected);
            JsfUtil.addSuccessMessage(message.getString("duyuru_silme_basarili"));
            getIlanListBySorguKriteri();
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
            ortakService.yeniDuyuruEkle(sessionInfo ,yeniDuyuru);
            ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.DUYURU, yeniDuyuru.getAciklama(), yeniDuyuru.getKisaAciklama(), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, null, BildirimTipi.ENUM.DUYURU, yeniDuyuru.getAciklama(), yeniDuyuru.getKisaAciklama(), BilgilendirmeTipi.ENUM.Notification);
            getIlanListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("duyuru_ekleme_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
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

    public IlanSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(IlanSorguKriteri sorguKriteri) {
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
}
