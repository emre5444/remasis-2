package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.AidatDataModel;
import com.ronin.managed.bean.lazydatamodel.DaireDataModel;
import com.ronin.model.*;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.BorcKalem;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.KaynakTipi;
import com.ronin.model.kriter.AidatSorguKriteri;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.TalepSorguKriteri;
import com.ronin.model.sorguSonucu.DaireBorcAlacakView;
import com.ronin.model.sorguSonucu.DaireBorcKalemView;
import com.ronin.service.IDaireService;
import com.ronin.service.IFinansalIslemlerService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "aidatIslemleriMB")
@ViewScoped
public class AidatIslemleriMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(AidatIslemleriMB.class);
    //servisler
    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{finansalIslemlerService}")
    private IFinansalIslemlerService finansalIslemlerService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{daireService}")
    private IDaireService daireService;

    private Borc yeniBorc = new Borc();

    //sorgu kriterleri
    private AidatSorguKriteri sorguKriteri = new AidatSorguKriteri();
    private DaireSorguKriteri daireSorguKriteri = new DaireSorguKriteri();

    //combolar
    private List<IAbstractEntity> blokList;
    private List<IAbstractEntity> borcKalemList;
    private List<IAbstractEntity> kaynakTipiList;
    private List<IAbstractEntity> daireTipiList;

    //data model
    private AidatDataModel dataModel;
    private DaireDataModel daireDataModel;

    //selected object
    private DaireBorc selected;
    private Daire selectedDaire;
    private String radioButtonSelection;

    //list
    private List<Daire> daireList;
    private List<Daire> selectedDaireList;
    private List<Daire> tempSelectedDaireList;
    private List<BorcKalem> selectedBorcKalemList;
    private List<DaireBorcKalemView> daireBorcKalemViews;
    private List<DaireBorcAlacakView> daireBorcAlacakViewList;

    private boolean skip;

    @PostConstruct
    public void init() {
        getFlushObjects();
        daireBorcKalemViews = new ArrayList<DaireBorcKalemView>();
        daireBorcAlacakViewList = new ArrayList<>();
        borcKalemList = ortakService.getListByNamedQueryWithSirket("BorcKalem.findAllWithSirket", sessionInfo);
    }

    public List<Kullanici> completePlayer(String query) {
        return ortakService.getKullaniciByName(query, sessionInfo);
    }

    public void getAidatListBySorguKriteri() {
        List<DaireBorc> dataList = finansalIslemlerService.getListCriteriaForPaging(0, 1000, sorguKriteri, sessionInfo);

        dataModel = new AidatDataModel(dataList);

        if (dataList == null || dataList.size() <= 0) {
            JsfUtil.addWarnMessage(message.getString("error.sonuc.yok"));
        }
    }

    public void deleteTempDaireToList() {
        selectedDaireList.remove(selectedDaire);
        daireList.add(selectedDaire);
    }

    public void getDaireListBySorguKriteri() {
        if (selectedDaireList != null) {
            for (Daire d : selectedDaireList) {
                daireSorguKriteri.getSelectedDaireList().add(d.getId());
            }
        }
        daireList = daireService.getListCriteriaForPaging(0, 2500, daireSorguKriteri, sessionInfo);
        daireDataModel = new DaireDataModel(daireList);
    }

    public String onFlowProcess(FlowEvent event) {
        if ((event.getNewStep().equals("son_duzenleme") || (skip && event.getNewStep().equals("borc_kalem"))) && !event.getOldStep().equals("confirm")) {
            if (radioButtonSelection.equals("toplu")) {
                selectedDaireList = new ArrayList<Daire>();
                selectedDaireList.addAll((List<Daire>) daireDataModel.getWrappedData());
            }
            daireBorcAlacakViewList.clear();
            for (Daire d : selectedDaireList) {
                DaireBorcAlacakView dba = new DaireBorcAlacakView();
                for (DaireBorcKalemView dbkView : daireBorcKalemViews) {
                    DaireBorcKalemView daireBorcKalemView = new DaireBorcKalemView();
                    daireBorcKalemView.setBorcKalem(dbkView.getBorcKalem());
                    daireBorcKalemView.setTutar(dbkView.getTutar());
                    dba.getBorcKalemViewList().add(daireBorcKalemView);
                }
                Borc borc = new Borc(yeniBorc);
                borc.setDurum(Durum.getAktifObject());
                borc.setIslemTipi(KaynakTipi.getBorcObject());
                borc.setSirket(sessionInfo.getSirket());
                dba.setBorc(borc);
                dba.setDaire(d);
                daireBorcAlacakViewList.add(dba);
            }
        }

        if (event.getNewStep().equals("aidat_tanimlama") &&
                (((selectedDaireList == null || selectedDaireList.isEmpty()) && radioButtonSelection.equals("normal"))
                        || (daireDataModel == null) && radioButtonSelection.equals("toplu"))) {
            JsfUtil.addWarnMessage(message.getString("selected_daire_bos_olamaz"));
            return "daire_secimi";
        }

        if (event.getNewStep().equals("son_duzenleme") &&
                daireBorcKalemViews != null && !daireBorcKalemViews.isEmpty()) {
            Double toplamKalemTutar = 0.0;
            for (DaireBorcKalemView view : daireBorcKalemViews) {
                if (view.getTutar() != null) {
                    toplamKalemTutar += view.getTutar();
                }
            }
            if (toplamKalemTutar > yeniBorc.getBorc()) {
                JsfUtil.addWarnMessage(message.getString("borc_borcKalem_kontrol"));
                return "borc_kalem";
            }
        }

        if (skip && event.getOldStep().equals("son_duzenleme") && !event.getNewStep().equals("confirm")) {
            return "aidat_tanimlama";
        } else if (skip && event.getNewStep().equals("borc_kalem")) {
            return "son_duzenleme";
        }
        return event.getNewStep();
    }

    public void getFlushObjects() {
        selectedDaire = (Daire) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaire");
        selected = (DaireBorc) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedDaireBorcObject");
        sorguKriteri = (AidatSorguKriteri) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("sorguKriteri");
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedDaireObject", selectedDaire);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }


    public void addDaireBorcKalemView() {
        daireBorcKalemViews.clear();
        for (BorcKalem bk : selectedBorcKalemList) {
            DaireBorcKalemView dbkv = new DaireBorcKalemView();
            dbkv.setBorcKalem(bk);
            if (!daireBorcKalemViews.contains(dbkv)) {
                daireBorcKalemViews.add(dbkv);
            }
        }
    }

    public void addTempDaire() {
        daireList.removeAll(tempSelectedDaireList);
        if (selectedDaireList == null) {
            selectedDaireList = new ArrayList<>();
        }
        selectedDaireList.addAll(tempSelectedDaireList);
    }

    public void islemleriTamamla() {
        try {
            if (radioButtonSelection.equals("toplu")) {
                selectedDaireList = new ArrayList<Daire>();
                selectedDaireList.addAll((List<Daire>) daireDataModel.getWrappedData());
            }
            for (DaireBorcAlacakView dba : daireBorcAlacakViewList) {
                DaireBorc daireBorc = new DaireBorc();
                daireBorc.setDaire(dba.getDaire());
                daireBorc.setDurum(Durum.getAktifObject());
                daireBorc.setBorc(dba.getBorc());
                finansalIslemlerService.addDaireBorc(daireBorc);

                for (DaireBorcKalemView dbk : dba.getBorcKalemViewList()) {
                    DaireBorcKalem daireBorcKalem = new DaireBorcKalem();
                    daireBorcKalem.setDurum(Durum.getAktifObject());
                    daireBorcKalem.setBorcKalem(dbk.getBorcKalem());
                    daireBorcKalem.setTutar(dbk.getTutar());
                    daireBorcKalem.setDaireBorc(daireBorc);
                    finansalIslemlerService.addDaireBorcKalem(daireBorcKalem);
                }
            }

        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public String update(Object object) {
        try {
            finansalIslemlerService.updateObject(object);
            storeFlashObjects();
            JsfUtil.addSuccessMessage(message.getString("islem_basarili"));
            return getBackPage();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
        return "";
    }

    public void delete(DaireBorc selectedDaireBorc) {
        try {
            setSelected(selectedDaireBorc);
            selected.getBorc().setDurum(Durum.getPasifObject());
            finansalIslemlerService.updateObject(selected);
            getAidatListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("islem_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }
    }

    public String redirectAfterWait() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return "aidatSorgula?faces-redirect=true";
    }

    public void onDaireBorcKalemRowEdit(RowEditEvent event) {
        DaireBorcKalem selectedDaireBorcKalem = (DaireBorcKalem) event.getObject();
        if (selectedDaireBorcKalem.getId() == null) {
            selectedDaireBorcKalem.setDurum(Durum.getAktifObject());
            selectedDaireBorcKalem.setDaireBorc(selected);
            getFinansalIslemlerService().addDaireBorcKalem(selectedDaireBorcKalem);
        } else {
            getFinansalIslemlerService().updateObject(selectedDaireBorcKalem);
        }
        getAidatListBySorguKriteri();
        JsfUtil.addSuccessMessage(message.getString("borc_kalem_ekleme_basarili"));
    }

    public void onDaireBorcKalemRowCancel(RowEditEvent event) {
        DaireBorcKalem selectedDaireBorcKalem = (DaireBorcKalem) event.getObject();
        if(selectedDaireBorcKalem.getId() == null){
            selected.getDaireBorcKalems().remove(selectedDaireBorcKalem);
            return;
        }
        getFinansalIslemlerService().deleteObject(selectedDaireBorcKalem);
        getAidatListBySorguKriteri();
        JsfUtil.addSuccessMessage(message.getString("borc_kalem_silme_basarili"));
    }

    public void addNewBorcKalem() {
        selected.getDaireBorcKalems().add(new DaireBorcKalem());
    }

    public boolean isTopluIslem() {
        return radioButtonSelection != null && radioButtonSelection.equals("toplu");
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

    public IFinansalIslemlerService getFinansalIslemlerService() {
        return finansalIslemlerService;
    }

    public void setFinansalIslemlerService(IFinansalIslemlerService finansalIslemlerService) {
        this.finansalIslemlerService = finansalIslemlerService;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public AidatSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(AidatSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public List<IAbstractEntity> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<IAbstractEntity> blokList) {
        this.blokList = blokList;
    }

    public AidatDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(AidatDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public DaireDataModel getDaireDataModel() {
        return daireDataModel;
    }

    public void setDaireDataModel(DaireDataModel daireDataModel) {
        this.daireDataModel = daireDataModel;
    }

    public DaireBorc getSelected() {
        return selected;
    }

    public void setSelected(DaireBorc selected) {
        this.selected = selected;
    }

    public List<IAbstractEntity> getKaynakTipiList() {
        return kaynakTipiList;
    }

    public void setKaynakTipiList(List<IAbstractEntity> kaynakTipiList) {
        this.kaynakTipiList = kaynakTipiList;
    }

    public Daire getSelectedDaire() {
        return selectedDaire;
    }

    public void setSelectedDaire(Daire selectedDaire) {
        this.selectedDaire = selectedDaire;
    }

    public String getRadioButtonSelection() {
        return radioButtonSelection;
    }

    public void setRadioButtonSelection(String radioButtonSelection) {
        if (!StringUtils.isEmpty(radioButtonSelection) && !radioButtonSelection.equals(this.radioButtonSelection)) {
            daireDataModel = null;
            selectedDaire = null;
            daireList = null;
            selectedDaireList = null;
            daireSorguKriteri = new DaireSorguKriteri();
            yeniBorc = new Borc();
            daireBorcKalemViews = new ArrayList<>();
        }
        this.radioButtonSelection = radioButtonSelection;
    }

    public Borc getYeniBorc() {
        return yeniBorc;
    }

    public void setYeniBorc(Borc yeniBorc) {
        this.yeniBorc = yeniBorc;
    }

    public List<Daire> getDaireList() {
        return daireList;
    }

    public void setDaireList(List<Daire> daireList) {
        this.daireList = daireList;
    }

    public DaireSorguKriteri getDaireSorguKriteri() {
        return daireSorguKriteri;
    }

    public void setDaireSorguKriteri(DaireSorguKriteri daireSorguKriteri) {
        this.daireSorguKriteri = daireSorguKriteri;
    }

    public List<IAbstractEntity> getDaireTipiList() {
        return daireTipiList;
    }

    public void setDaireTipiList(List<IAbstractEntity> daireTipiList) {
        this.daireTipiList = daireTipiList;
    }

    public IDaireService getDaireService() {
        return daireService;
    }

    public void setDaireService(IDaireService daireService) {
        this.daireService = daireService;
    }

    public List<Daire> getSelectedDaireList() {
        return selectedDaireList;
    }

    public void setSelectedDaireList(List<Daire> selectedDaireList) {
        this.selectedDaireList = selectedDaireList;
    }

    public List<IAbstractEntity> getBorcKalemList() {
        return borcKalemList;
    }

    public void setBorcKalemList(List<IAbstractEntity> borcKalemList) {
        this.borcKalemList = borcKalemList;
    }

    public List<BorcKalem> getSelectedBorcKalemList() {
        return selectedBorcKalemList;
    }

    public void setSelectedBorcKalemList(List<BorcKalem> selectedBorcKalemList) {
        this.selectedBorcKalemList = selectedBorcKalemList;
    }

    public List<DaireBorcKalemView> getDaireBorcKalemViews() {
        return daireBorcKalemViews;
    }

    public void setDaireBorcKalemViews(List<DaireBorcKalemView> daireBorcKalemViews) {
        this.daireBorcKalemViews = daireBorcKalemViews;
    }

    public List<DaireBorcAlacakView> getDaireBorcAlacakViewList() {
        return daireBorcAlacakViewList;
    }

    public void setDaireBorcAlacakViewList(List<DaireBorcAlacakView> daireBorcAlacakViewList) {
        this.daireBorcAlacakViewList = daireBorcAlacakViewList;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<Daire> getTempSelectedDaireList() {
        return tempSelectedDaireList;
    }

    public void setTempSelectedDaireList(List<Daire> tempSelectedDaireList) {
        this.tempSelectedDaireList = tempSelectedDaireList;
    }
}
