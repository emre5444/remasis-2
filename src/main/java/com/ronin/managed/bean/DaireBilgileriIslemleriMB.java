package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Ilce;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DaireDataModel;
import com.ronin.model.Daire;
import com.ronin.model.DaireArac;
import com.ronin.model.DaireSakin;
import com.ronin.model.DaireYardimci;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.service.IDaireService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "daireBilgileriIslemleriMB")
@ViewScoped
public class DaireBilgileriIslemleriMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireBilgileriIslemleriMB.class);
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

    private DaireSakin daireSakin = new DaireSakin();
    private DaireArac daireArac = new DaireArac();
    private DaireYardimci daireYardimci = new DaireYardimci();
    private Kullanici yeniKullanici = new Kullanici();

    //daire islemleri
    private Daire selectedDaire;
    private Kullanici selectedKullanici;

    private List<IAbstractEntity> ilList;
    private List<IAbstractEntity> ilceList;

    @PostConstruct
    public void init() {
        getFlushObjects();
    }

    public void getFlushObjects() {
        DaireSakin ds = (DaireSakin) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireSakinObject");
        if(ds != null)
            daireSakin = ds;
        DaireArac da = (DaireArac) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireAracObject");
        if(da != null)
            daireArac = da;
        DaireYardimci dy = (DaireYardimci) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireYardimciObject");
        if(dy != null)
            daireYardimci = dy;
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
        selectedDaire = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireObject");
        selectedKullanici = (Kullanici) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedKullaniciObject");
        sorguKriteri = (DaireSorguKriteri) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("sorguKriteri");
    }

    public void findIlceListByIl(){
        ilceList = ortakService.getIlceListByNamedQueryWithIl("Ilce.findByIlId" , daireSakin.getKullanici().getIl());
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireObject", selectedDaire);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }

    public String daireSakinGuncelleme() {
        daireService.daireSakinGuncelleme(daireSakin, sessionInfo);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("info_daire_sakin_guncelleme_basarili"));
        return getBackPage();
    }

    public String daireAracGuncelleme() {
        daireService.daireAracGuncelleme(daireArac, sessionInfo);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("info_daire_arac_guncelleme_basarili"));
        return getBackPage();
    }

    public String daireYardimciGuncelleme() {
        daireService.daireYardimciGuncelleme(daireYardimci, sessionInfo);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("info_daire_yardimci_guncelleme_basarili"));
        return getBackPage();
    }

    public String daireSakinYenikayit() {
        daireSakin.setKullanici(yeniKullanici);
        daireSakin.setBagliKullanici(selectedKullanici);
        daireSakin.setDaire(selectedDaire);
        daireService.daireSakinEkleme(daireSakin, sessionInfo);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("info_daire_sakin_ekleme_basarili"));
        return getBackPage();
    }

    public String daireAracYenikayit() {
        daireArac.setKullanici(yeniKullanici);
        daireArac.setDaire(selectedDaire);
        daireService.daireAracEkleme(daireArac, sessionInfo);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("info_daire_arac_ekleme_basarili"));
        return getBackPage();
    }

    public String daireYardimciYenikayit() {
        daireYardimci.setKullanici(yeniKullanici);
        daireYardimci.setDaire(selectedDaire);
        daireService.daireYardimciEkleme(daireYardimci, sessionInfo);
        storeFlashObjects();
        JsfUtil.addSuccessMessage(message.getString("info_daire_yardimci_ekleme_basarili"));
        return getBackPage();
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

    public Kullanici getSelectedKullanici() {
        return selectedKullanici;
    }

    public void setSelectedKullanici(Kullanici selectedKullanici) {
        this.selectedKullanici = selectedKullanici;
    }

    public Daire getSelectedDaire() {
        return selectedDaire;
    }

    public void setSelectedDaire(Daire selectedDaire) {
        this.selectedDaire = selectedDaire;
    }

    public DaireSakin getDaireSakin() {
        return daireSakin;
    }

    public void setDaireSakin(DaireSakin daireSakin) {
        this.daireSakin = daireSakin;
    }

    public List<IAbstractEntity> getIlceList() {
        return ilceList;
    }

    public void setIlceList(List<IAbstractEntity> ilceList) {
        this.ilceList = ilceList;
    }

    public List<IAbstractEntity> getIlList() {
        return ilList;
    }

    public void setIlList(List<IAbstractEntity> ilList) {
        this.ilList = ilList;
    }

    public Kullanici getYeniKullanici() {
        return yeniKullanici;
    }

    public void setYeniKullanici(Kullanici yeniKullanici) {
        this.yeniKullanici = yeniKullanici;
    }

    public DaireArac getDaireArac() {
        return daireArac;
    }

    public void setDaireArac(DaireArac daireArac) {
        this.daireArac = daireArac;
    }

    public DaireYardimci getDaireYardimci() {
        return daireYardimci;
    }

    public void setDaireYardimci(DaireYardimci daireYardimci) {
        this.daireYardimci = daireYardimci;
    }
}