package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.DaireBelgeDataModel;
import com.ronin.managed.bean.lazydatamodel.TalepDataModel;
import com.ronin.model.DaireBelge;
import com.ronin.model.DaireBorc;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.ItirazTalebi;
import com.ronin.model.TalepDaire;
import com.ronin.model.constant.Belge;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.kriter.TalepSorguKriteri;
import com.ronin.service.IFileUploadService;
import com.ronin.service.ITalepService;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "mailIslemleriMB")
@ViewScoped
public class MailIslemleriMB implements Serializable {

    public static Logger logger = Logger.getLogger(MailIslemleriMB.class);
    //servisler
    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    private String text;


    @PostConstruct
    public void init() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public void buttonAction(String deger) {
        if (StringUtils.isEmpty(text)) {
            text = deger;
        } else {
            text = text.concat(deger);
        }
    }

    public void clearText() {
        text = null;
    }
}
