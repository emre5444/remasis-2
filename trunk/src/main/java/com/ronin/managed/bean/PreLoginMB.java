package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.KullaniciSirketDataModel;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.Sirket;
import com.ronin.model.constant.LogTipi;
import com.ronin.utils.DateUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;


@ManagedBean(name = "preLoginMB")
@ViewScoped
public class PreLoginMB implements Serializable {

    public static Logger logger = Logger.getLogger(PreLoginMB.class);

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    private KullaniciSirketDataModel dataModel;

    private KullaniciSirket selected;

    List<KullaniciSirket> kullaniciSirketList;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    public PreLoginMB() {
    }

    @PostConstruct
    public void init() {
        lastLoginDateSetter();
        if (sessionInfo.getSirket() != null) {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "dashboard.xhtml?faces-redirect=true");
        } else {
            kullaniciSirketList = sessionInfo.getKullanici().getKullaniciSirketList();
            dataModel = new KullaniciSirketDataModel(kullaniciSirketList);
            if (kullaniciSirketList.isEmpty() || kullaniciSirketList.size() == 1) {
                sessionInfo.setSirket(((KullaniciSirket) kullaniciSirketList.get(0)).getSirket());
                //login log kaydi atilir.
                ortakService.createErisimLog(sessionInfo, sessionInfo.getKullanici(), LogTipi.getLoginObject(), "");
                FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "dashboard.xhtml?faces-redirect=true");
            }
        }
    }

    private void lastLoginDateSetter() {
        Kullanici kullanici = sessionInfo.getKullanici();
        kullanici.setLastLogin(DateUtils.getNow());
        ortakService.update(kullanici);
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public KullaniciSirketDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(KullaniciSirketDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public List<KullaniciSirket> getKullaniciSirketList() {
        return kullaniciSirketList;
    }

    public void setKullaniciSirketList(List<KullaniciSirket> kullaniciSirketList) {
        this.kullaniciSirketList = kullaniciSirketList;
    }

    public KullaniciSirket getSelected() {
        return selected;
    }

    public void setSelected(KullaniciSirket selected) {
        this.selected = selected;
        if (selected != null) {
            sessionInfo.setSirket(selected.getSirket());
        }
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }

    public String continueToIndex() {
        if (selected == null) {
            JsfUtil.addErrorMessage(message.getString("secim_yapilmali"));
            return "";
        } else {
            //login log kaydi atilir.
            ortakService.createErisimLog(sessionInfo, sessionInfo.getKullanici(), LogTipi.getLoginObject(), "");
            return "dashboard.xhtml?faces-redirect=true";
        }
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }
}
