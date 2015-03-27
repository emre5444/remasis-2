package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DaireDataModel;
import com.ronin.model.Daire;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.LogTipi;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.service.IDaireService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "daireMB")
@ViewScoped
public class DaireMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);
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

    //sorgu kriterleri
    private DaireSorguKriteri sorguKriteri = new DaireSorguKriteri();

    private Daire yeniDaire = new Daire();

    //daire islemleri
    private Daire selected;
    private DaireDataModel dataModel;
    List<Daire> daireSorguSonucu;
    private List<IAbstractEntity> blokList;
    private List<IAbstractEntity> durumList;
    private List<IAbstractEntity> daireTipiList;

    @PostConstruct
    public void init() {
        setUserRolInfos();
        getFlushObjects();
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket",sessionInfo);
        durumList = ortakService.getListByNamedQuery("Durum.findAll");
        daireTipiList = ortakService.getListByNamedQuery("DaireTipi.findAll");
    }

    public void getFlushObjects() {
        DaireSorguKriteri sk = (DaireSorguKriteri) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("sorguKriteri");
        if (sk != null) {
            sorguKriteri = sk;
            getDaireListBySorguKriteri();
        }
    }

    public void getDaireListBySorguKriteri() {
        List<Daire> dataList = daireService.getListCriteriaForPaging(0, 500, sorguKriteri , sessionInfo);
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
        if (!sessionInfo.isAdminMi()) {
            sorguKriteri.setKullanici(sessionInfo.getKullanici());
            getDaireListBySorguKriteri();
        }
    }


    //page navigations
    public String daireGoruntule(Daire selectedDaire) {
        setSelected(selectedDaire);
        ortakService.createErisimLog(sessionInfo ,sessionInfo.getKullanici(), LogTipi.getDaireGoruntuleObject(), label.getString("daire_kodu") + ":" + selected.getDaireKodu());
        storeFlashObjects();
        return "daireGoruntuleme.xhtml";
    }

    public String daireGuncelleme(Daire selectedDaire) {
        setSelected(selectedDaire);
        storeFlashObjects();
        return "daireGuncelleme.xhtml";
    }

    public String daireEkleme() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backPage", "daireSorgula.xhtml");
        return "daireEkleme.xhtml";
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireObject", selected);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backPage", "daireSorgula.xhtml");
    }

    public void delete(Daire daire) {
        try {
            setSelected(daire);
            selected.setDurum(Durum.getPasifObject());
            daireService.delete(selected);
            getDaireListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("islem_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }


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


    public DaireSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(DaireSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
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

    public List<Daire> getDaireSorguSonucu() {
        return daireSorguSonucu;
    }

    public void setDaireSorguSonucu(List<Daire> daireSorguSonucu) {
        this.daireSorguSonucu = daireSorguSonucu;
    }

    public List<IAbstractEntity> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<IAbstractEntity> blokList) {
        this.blokList = blokList;
    }

    public List<IAbstractEntity> getDurumList() {
        return durumList;
    }

    public void setDurumList(List<IAbstractEntity> durumList) {
        this.durumList = durumList;
    }

    public Daire getYeniDaire() {
        return yeniDaire;
    }

    public void setYeniDaire(Daire yeniDaire) {
        this.yeniDaire = yeniDaire;
    }

    public List<IAbstractEntity> getDaireTipiList() {
        return daireTipiList;
    }

    public void setDaireTipiList(List<IAbstractEntity> daireTipiList) {
        this.daireTipiList = daireTipiList;
    }
}