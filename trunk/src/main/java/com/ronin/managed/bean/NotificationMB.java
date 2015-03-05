package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.NotificationDataModel;
import com.ronin.model.Notification;
import com.ronin.model.constant.Durum;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "notificationMB")
@ViewScoped
public class NotificationMB implements Serializable {

    public static Logger logger = Logger.getLogger(NotificationMB.class);

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    List<Notification> notificationList;
    NotificationDataModel notificationDataModel;

    Notification selected;


    @PostConstruct
    public void init() {
        notificationList = ortakService.getAllNotificationList(sessionInfo, 10);
        notificationDataModel = new NotificationDataModel(notificationList);
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

    public NotificationDataModel getNotificationDataModel() {
        return notificationDataModel;
    }

    public void setNotificationDataModel(NotificationDataModel notificationDataModel) {
        this.notificationDataModel = notificationDataModel;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public Notification getSelected() {
        return selected;
    }

    public void setSelected(Notification selected) {
        this.selected = selected;
    }

    public void onRowSelect() throws IOException {
        if (selected != null && selected.getBildirimTipi() != null) {
           String pathRoot =  FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            if (selected.getBildirimTipi().isAidatMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/rezidansIslemleri/daireSorgula.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isAidatItirazMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/talepIslemleri/talepSorgula.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isAnketMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/sitePaylasimIslemleri/anketSorgulama.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isArizaMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/talepIslemleri/talepSorgula.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isBelgeTalepMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/talepIslemleri/talepSorgula.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isDuyuruMu()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/sitePaylasimIslemleri/duyuruSorgulama.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isIlanEklemeMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot + "/pages/sitePaylasimIslemleri/ilanSorgulama.jsf?faces-redirect=true");
            } else if (selected.getBildirimTipi().isSikayetMi()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(pathRoot +"/pages/talepIslemleri/talepSorgula.jsf?faces-redirect=true");
            }
        }
    }

    public void updateStatus() {
        for (Notification notification : notificationList) {
            if (notification.getDurum().equals(Durum.getAktifObject())) {
                notification.setDurum(Durum.getPasifObject());
                ortakService.update(notification);
            }
        }
    }

    public int aktifNotificationsCount() {
        int count = 0;
        for (Notification notification : notificationList) {
            if (notification.getDurum().equals(Durum.getAktifObject()))
                count++;
        }
        return count;
    }

}
