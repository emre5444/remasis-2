package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.common.service.IRolService;
import com.ronin.managed.bean.lazydatamodel.DaireDataModel;
import com.ronin.managed.bean.lazydatamodel.KullaniciDaireDataModel;
import com.ronin.managed.bean.lazydatamodel.KullaniciDataModel;
import com.ronin.model.Daire;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.KullaniciSorguKriteri;
import com.ronin.service.IDaireService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "kullaniciController")
@ViewScoped
public class KullaniciMB implements Serializable {

    public static Logger logger = Logger.getLogger(KullaniciMB.class);

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{daireService}")
    private IDaireService daireService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{rolService}")
    private IRolService rolService;

    private KullaniciSorguKriteri sorguKriteri = new KullaniciSorguKriteri();
    private Kullanici selected;
    private Kullanici yeniKullanici = new Kullanici();
    private KullaniciDataModel dataModel;
    private Durum durum;
    private List<IAbstractEntity> durumList;

    //kullanici daire islemleri
    private KullaniciDaireDataModel kullaniciDaireDataModel;
    private KullaniciDaireDataModel daireSorguList;
    private KullaniciDaire selectedKullaniciDaire;

    //daire islemleri
    private Daire selectedDaire;
    private DaireDataModel daireDataModel;
    private DaireSorguKriteri daireSorguKriteri = new DaireSorguKriteri();
    private List<KullaniciDaire> kullaniciDaireListesi;

    public Boolean islemTamamlandiMi = false;
    private List<IAbstractEntity> rolTipiList;
    private List<IAbstractEntity> rolList;
    private List<IAbstractEntity> kullaniciTipiList;

    private DualListModel<Rol> selectedRolList;
    List<Rol> sourceRol = new ArrayList<Rol>();
    List<Rol> targetRol = new ArrayList<Rol>();

    private boolean tumKriterlerMi;
    //combos
    private List<IAbstractEntity> blokList;

    @PostConstruct
    public void init() {
        selectedRolList = new DualListModel<Rol>(sourceRol, targetRol);
        durumList = ortakService.getListByNamedQuery("Durum.findAll");
        rolTipiList = ortakService.getListByNamedQuery("EvetHayir.findAll");
        rolList = ortakService.getListByNamedQuery("Rol.findAll");
        kullaniciTipiList = ortakService.getListByNamedQuery("KullaniciTipi.findAll");
        if (!sessionInfo.isAdminMi()) {
            sorguKriteri.setAd(sessionInfo.getKullanici().getAd());
            sorguKriteri.setSoyad(sessionInfo.getKullanici().getSoyad());
        }
        prepareCombos();
    }

    public void getDaireListByKullanici() {
        kullaniciDaireListesi = kullaniciService.getDaireListByKullanici(selected);
        kullaniciDaireDataModel = new KullaniciDaireDataModel(kullaniciDaireListesi);
    }

    public void getDaireListBySorguKriteri() {
        List<Daire> daireDataList = daireService.getListCriteriaForPaging(0, 500, daireSorguKriteri, sessionInfo);
        daireDataModel = new DaireDataModel(daireDataList);
    }

    public void addTempDaireToKullanici() {
        kullaniciDaireListesi = kullaniciService.addTempDaireToKullanici(kullaniciDaireListesi, selected, selectedDaire);
        kullaniciDaireDataModel = new KullaniciDaireDataModel(kullaniciDaireListesi);
        daireDataModel = null;
        daireSorguKriteri = new DaireSorguKriteri();
    }

    public void deleteTempDaireToKullanici() {
        kullaniciDaireListesi = kullaniciService.deleteTempDaireToKullanici(kullaniciDaireListesi, selectedKullaniciDaire);
        kullaniciDaireDataModel = new KullaniciDaireDataModel(kullaniciDaireListesi);
    }

    public void prepareRolList() {
        List<Rol> allRollList = rolService.getAllRolList(sessionInfo);
        List<Rol> userRolList = rolService.getRolListByKullanici(selected , sessionInfo);

        sourceRol = getDifferenceOfRolLists(allRollList, userRolList);
        targetRol = userRolList;

        selectedRolList = new DualListModel<Rol>(sourceRol, targetRol);

    }

    public void prepareKullaniciDaire() {
        getDaireListByKullanici();
        daireDataModel = null;
        daireSorguKriteri = new DaireSorguKriteri();
    }

    public void updateKullaniciRolList() {
        rolService.updateKullaniciRol(selectedRolList.getTarget(), selected);
        JsfUtil.addSuccessMessage(message.getString("kullanici_rol_iliskilendirme_basarili"));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('kullaniciRolPopup').hide()");
    }

    public void prepareCombos() {
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket",sessionInfo);
    }

    public void updateKullaniciDaireList() {
        kullaniciService.updateKullaniciDaire((List<KullaniciDaire>) kullaniciDaireDataModel.getWrappedData(), selected);
        getKullaniciListBySorguKriteri();
        JsfUtil.addSuccessMessage(message.getString("kullanici_daire_iliskilendirme_basarili"));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('kullaniciDairePopup').hide()");
    }

    public List<Rol> getDifferenceOfRolLists(List<Rol> list1, List<Rol> list2) {
        List<Rol> resultList = new ArrayList<>();
        for (Rol r1 : list1) {
            if (!list2.contains(r1)) {
                resultList.add(r1);
            }
        }
        return resultList;
    }

    public void getKullaniciListBySorguKriteri() {
        try {
            List<Kullanici> dataList = kullaniciService.getListCriteriaForPaging(0, 500, sorguKriteri, sessionInfo);
            dataModel = new KullaniciDataModel(dataList);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addErrorMessage(e.toString());
        }
    }

    public void add() {
        try {
            yeniKullanici.setDurum(Durum.getAktifObject());
            Kullanici k = kullaniciService.getKullaniciByUsername(yeniKullanici.getUsername());

            KullaniciSirket kullaniciSirket =new KullaniciSirket();
            kullaniciSirket.setSirket(sessionInfo.getSirket());
            kullaniciSirket.setKullanici(yeniKullanici);
            kullaniciSirket.setDurum(Durum.getAktifObject());

            if (k == null) {
                kullaniciService.addKullanici(yeniKullanici);
                kullaniciService.addKullaniciSirket(kullaniciSirket);
                JsfUtil.addSuccessMessage(message.getString("kullanici_ekleme_basarili"));
            } else {
                JsfUtil.addErrorMessage(message.getString("kullanici_kodu_zaten_mevcut"));
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage(e.toString());
        }
    }

    public void update() {
        try {
            Kullanici k = null;
            if (k == null) {
                kullaniciService.updateKullanici(selected);
                getKullaniciListBySorguKriteri();
                islemTamamlandiMi = true;
                JsfUtil.addSuccessMessage(message.getString("kullanici_guncelleme_basarili"));

                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PF('kullaniciGuncellemePopup').hide()");

            } else {
                JsfUtil.addErrorMessage(message.getString("kullanici_kodu_zaten_mevcut"));
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage(e.toString());
        }
    }

    public void delete() {
        try {
            kullaniciService.deleteKullanici(selected);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage(e.toString());
        }
    }

    public void resetPassword(){
        selected.setPassword("123456");
        kullaniciService.updateKullaniciWithPasword(selected);
        JsfUtil.addSuccessMessage(message.getString("sifre_sifirlama_basarili"));
    }

    public Kullanici getSelected() {
        return selected;
    }

    public void setSelected(Kullanici selected) {
        this.selected = selected;
    }

    public KullaniciSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(KullaniciSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public KullaniciDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(KullaniciDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public List<IAbstractEntity> getDurumList() {
        return durumList;
    }

    public void setDurumList(List<IAbstractEntity> durumList) {
        this.durumList = durumList;
    }

    public KullaniciDaireDataModel getDaireSorguList() {
        return daireSorguList;
    }

    public void setDaireSorguList(KullaniciDaireDataModel daireSorguList) {
        this.daireSorguList = daireSorguList;
    }

    public KullaniciDaireDataModel getKullaniciDaireDataModel() {
        return kullaniciDaireDataModel;
    }

    public void setKullaniciDaireDataModel(KullaniciDaireDataModel kullaniciDaireDataModel) {
        this.kullaniciDaireDataModel = kullaniciDaireDataModel;
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

    public Kullanici getYeniKullanici() {
        return yeniKullanici;
    }

    public void setYeniKullanici(Kullanici yeniKullanici) {
        this.yeniKullanici = yeniKullanici;
    }

    public Boolean getIslemTamamlandiMi() {
        return islemTamamlandiMi;
    }

    public void setIslemTamamlandiMi(Boolean islemTamamlandiMi) {
        this.islemTamamlandiMi = islemTamamlandiMi;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public List<Rol> getTargetRol() {
        return targetRol;
    }

    public void setTargetRol(List<Rol> targetRol) {
        this.targetRol = targetRol;
    }

    public List<Rol> getSourceRol() {
        return sourceRol;
    }

    public void setSourceRol(List<Rol> sourceRol) {
        this.sourceRol = sourceRol;
    }

    public DualListModel<Rol> getSelectedRolList() {
        return selectedRolList;
    }

    public void setSelectedRolList(DualListModel<Rol> selectedRolList) {
        this.selectedRolList = selectedRolList;
    }

    public IRolService getRolService() {
        return rolService;
    }

    public void setRolService(IRolService rolService) {
        this.rolService = rolService;
    }

    public List<IAbstractEntity> getRolTipiList() {
        return rolTipiList;
    }

    public void setRolTipiList(List<IAbstractEntity> rolTipiList) {
        this.rolTipiList = rolTipiList;
    }

    public List<IAbstractEntity> getRolList() {
        return rolList;
    }

    public void setRolList(List<IAbstractEntity> rolList) {
        this.rolList = rolList;
    }

    public KullaniciDaire getSelectedKullaniciDaire() {
        return selectedKullaniciDaire;
    }

    public void setSelectedKullaniciDaire(KullaniciDaire selectedKullaniciDaire) {
        this.selectedKullaniciDaire = selectedKullaniciDaire;
    }

    public Daire getSelectedDaire() {
        return selectedDaire;
    }

    public void setSelectedDaire(Daire selectedDaire) {
        this.selectedDaire = selectedDaire;
    }

    public DaireDataModel getDaireDataModel() {
        return daireDataModel;
    }

    public void setDaireDataModel(DaireDataModel daireDataModel) {
        this.daireDataModel = daireDataModel;
    }

    public DaireSorguKriteri getDaireSorguKriteri() {
        return daireSorguKriteri;
    }

    public void setDaireSorguKriteri(DaireSorguKriteri daireSorguKriteri) {
        this.daireSorguKriteri = daireSorguKriteri;
    }

    public IDaireService getDaireService() {
        return daireService;
    }

    public void setDaireService(IDaireService daireService) {
        this.daireService = daireService;
    }

    public List<KullaniciDaire> getKullaniciDaireListesi() {
        return kullaniciDaireListesi;
    }

    public void setKullaniciDaireListesi(List<KullaniciDaire> kullaniciDaireListesi) {
        this.kullaniciDaireListesi = kullaniciDaireListesi;
    }

    public List<IAbstractEntity> getKullaniciTipiList() {
        return kullaniciTipiList;
    }

    public void setKullaniciTipiList(List<IAbstractEntity> kullaniciTipiList) {
        this.kullaniciTipiList = kullaniciTipiList;
    }

    public List<IAbstractEntity> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<IAbstractEntity> blokList) {
        this.blokList = blokList;
    }

    public boolean isTumKriterlerMi() {
        return tumKriterlerMi;
    }

    public void setTumKriterlerMi(boolean tumKriterlerMi) {
        this.tumKriterlerMi = tumKriterlerMi;
    }
}
