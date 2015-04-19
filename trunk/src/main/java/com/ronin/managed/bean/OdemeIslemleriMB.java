package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.AidatDataModel;
import com.ronin.managed.bean.lazydatamodel.DaireDataModel;
import com.ronin.model.Borc;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.KaynakTipi;
import com.ronin.model.kriter.AidatSorguKriteri;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.sorguSonucu.DaireBorcAlacakView;
import com.ronin.service.IDaireService;
import com.ronin.service.IFinansalIslemlerService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "odemeIslemleriMB")
@ViewScoped
public class OdemeIslemleriMB implements Serializable {

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
    private List<DaireBorcAlacakView> daireBorcAlacakViewList;

    private String currentStep;

    @PostConstruct
    public void init() {
        prepareCombos();
        setUserRolInfos();
        daireBorcAlacakViewList = new ArrayList<>();
    }

    public void setUserRolInfos() {
        if (!sessionInfo.isAdminMi()) {
            sorguKriteri.setKullanici(sessionInfo.getKullanici());
        }
    }

    public void prepareCombos() {
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket", sessionInfo);
    }

    public List<Kullanici> completePlayer(String query) {
        return ortakService.getKullaniciByName(query, sessionInfo);
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

    public void addTempDaire() {
        daireList.removeAll(tempSelectedDaireList);
        if (selectedDaireList == null) {
            selectedDaireList = new ArrayList<>();
        }
        selectedDaireList.addAll(tempSelectedDaireList);
    }


    public String onFlowProcess(FlowEvent event) {
        if (event.getNewStep().equals("son_duzenleme")) {
            if (radioButtonSelection.equals("toplu")) {
                selectedDaireList = new ArrayList<Daire>();
                selectedDaireList.addAll((List<Daire>) daireDataModel.getWrappedData());
            }
            daireBorcAlacakViewList.clear();
            for (Daire d : selectedDaireList) {
                DaireBorcAlacakView dba = new DaireBorcAlacakView();
                Borc borc = new Borc(yeniBorc);
                borc.setDurum(Durum.getAktifObject());
                borc.setIslemTarihi(yeniBorc.getOdemeVadesi());
                borc.setIslemTipi(KaynakTipi.getOdemeObject());
                borc.setSirket(sessionInfo.getSirket());
                dba.setBorc(borc);
                dba.setDaire(d);
                daireBorcAlacakViewList.add(dba);
            }
        }

        if (event.getNewStep().equals("odeme_tanimlama") &&
                (((selectedDaireList == null || selectedDaireList.isEmpty()) && radioButtonSelection.equals("normal"))
                        || (daireDataModel == null) && radioButtonSelection.equals("toplu"))) {
            JsfUtil.addWarnMessage(message.getString("selected_daire_bos_olamaz"));
            setCurrentStep("daire_secimi");
            RequestContext.getCurrentInstance().update("main_form:steps");
            return "daire_secimi";
        }
        setCurrentStep(event.getNewStep());
        RequestContext.getCurrentInstance().update("main_form:steps");
        return event.getNewStep();
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
            }

        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
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

    public int activeIndexFinder() {
        if (org.apache.commons.lang.StringUtils.isNotEmpty(currentStep)) {
            if ("decision".equals(currentStep))
                return 0;
            else if ("daire_secimi".equals(currentStep))
                return 1;
            else if ("odeme_tanimlama".equals(currentStep))
                return 2;
            else if ("son_duzenleme".equals(currentStep))
                return 3;
            else if ("confirm".equals(currentStep))
                return 4;
        }
        return 0;
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

    public List<DaireBorcAlacakView> getDaireBorcAlacakViewList() {
        return daireBorcAlacakViewList;
    }

    public void setDaireBorcAlacakViewList(List<DaireBorcAlacakView> daireBorcAlacakViewList) {
        this.daireBorcAlacakViewList = daireBorcAlacakViewList;
    }

    public List<Daire> getTempSelectedDaireList() {
        return tempSelectedDaireList;
    }

    public void setTempSelectedDaireList(List<Daire> tempSelectedDaireList) {
        this.tempSelectedDaireList = tempSelectedDaireList;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }
}
