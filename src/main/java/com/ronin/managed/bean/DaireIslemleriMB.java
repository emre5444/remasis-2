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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "daireIslemleriMB")
@ViewScoped
public class DaireIslemleriMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireIslemleriMB.class);
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

    public List<Daire> daireList = new ArrayList<>();
    private DaireDataModel daireDataModel;

    @PostConstruct
    public void init() {
        getFlushObjects();
        prepareCombo();
    }

    public void prepareCombo() {
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket", sessionInfo);
        durumList = ortakService.getListByNamedQuery("Durum.findAll");
        daireTipiList = ortakService.getListByNamedQuery("DaireTipi.findAll");
    }

    public void getFlushObjects() {
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
        selected = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireObject");
        sorguKriteri = (DaireSorguKriteri) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("sorguKriteri");
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }

    public String update() {
        daireService.update(selected);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("daire_guncelleme_basarili"));
        return getBackPage();
    }

    public String yeniDaireEkleme() {
        daireService.add(yeniDaire);
        JsfUtil.addSuccessMessage(message.getString("daire_ekleme_basarili"));
        return getBackPage();
    }

    public String yeniDaireListEkle(){
        daireService.addDaireListToBlok(sessionInfo,daireList);
        JsfUtil.addSuccessMessage(message.getString("daire_ekleme_basarili"));
        return "daireSorgula.xhtml";
    }

    public void daireyiListeyeEkle() {
        if (!daireService.isDaireListedeVarMi(daireList, yeniDaire)) {
            daireList.add(yeniDaire);
            daireDataModel = new DaireDataModel(daireList);
            yeniDaire = new Daire();
        } else {
            JsfUtil.addErrorMessage(message.getString("error_ayni_daire_eklenemez"));
        }
    }

    public void deleteTempDaire(Daire deletedDaire) {
        daireList = daireService.deleteTempDaire(daireList, deletedDaire);
        daireDataModel = new DaireDataModel(daireList);
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

    public DaireDataModel getDaireDataModel() {
        return daireDataModel;
    }

    public void setDaireDataModel(DaireDataModel daireDataModel) {
        this.daireDataModel = daireDataModel;
    }

    public List<Daire> getDaireList() {
        return daireList;
    }

    public void setDaireList(List<Daire> daireList) {
        this.daireList = daireList;
    }
}