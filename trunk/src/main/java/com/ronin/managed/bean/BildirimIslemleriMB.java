package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Rol;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.BildirimTipiDataModel;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.service.IBildirimService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "bildirimIslemleriMB")
@ViewScoped
public class BildirimIslemleriMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{bildirimService}")
    private IBildirimService bildirimService;


    private BildirimTipiSorguKriteri sorguKriteri = new BildirimTipiSorguKriteri();

    private BildirimTipi selectedBildirimTipi;

    private List<IAbstractEntity> bildirimTipiList;

    private DualListModel<Rol> selectedRolList;
    List<Rol> sourceRol = new ArrayList<Rol>();
    List<Rol> targetRol = new ArrayList<Rol>();

    @PostConstruct
    public void init() {
        getFlushObjects();
        prepareRolList();
        selectedRolList = new DualListModel<Rol>(sourceRol, targetRol);
        bildirimTipiList = ortakService.getListByNamedQuery("BildirimTipi.findAll");
    }

    public void getFlushObjects() {
        setBackPage((String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backPage"));
        selectedBildirimTipi = (BildirimTipi) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("selectedBildirimTipiObject");
        sorguKriteri = (BildirimTipiSorguKriteri) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("bildirimTipiSorguKriteri");
    }

    public String geriDon() {
        storeFlashObjects();
        return getBackPage();
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }

    public String updateBildirimTipiRolList() {
        bildirimService.updateBldirimTipiRol(selectedRolList.getTarget(), selectedBildirimTipi, sessionInfo);
        JsfUtil.addSuccessMessage(message.getString("bildirim_tipi_hepef_kitle_belirleme_basarili"));
        storeFlashObjects();
        return getBackPage();
    }

    public void prepareRolList() {
        List<Rol> allRollList = bildirimService.getAllRolList(sessionInfo);
        List<Rol> userRolList = bildirimService.getRolListByBildirimTipi(selectedBildirimTipi, sessionInfo);

        sourceRol = getDifferenceOfRolLists(allRollList, userRolList);
        targetRol = userRolList;

        selectedRolList = new DualListModel<Rol>(sourceRol, targetRol);

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


    public List<IAbstractEntity> getBildirimTipiList() {
        return bildirimTipiList;
    }

    public void setBildirimTipiList(List<IAbstractEntity> bildirimTipiList) {
        this.bildirimTipiList = bildirimTipiList;
    }

    public BildirimTipi getSelectedBildirimTipi() {
        return selectedBildirimTipi;
    }

    public void setSelectedBildirimTipi(BildirimTipi selectedBildirimTipi) {
        this.selectedBildirimTipi = selectedBildirimTipi;
    }

    public BildirimTipiSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(BildirimTipiSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public IBildirimService getBildirimService() {
        return bildirimService;
    }

    public void setBildirimService(IBildirimService bildirimService) {
        this.bildirimService = bildirimService;
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
}
