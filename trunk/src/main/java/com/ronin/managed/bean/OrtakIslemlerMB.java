package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.common.service.OrtakService;
import com.ronin.managed.bean.lazydatamodel.BlokDataModel;
import com.ronin.model.constant.Blok;
import com.ronin.model.kriter.BlokSorguKriteri;
import com.ronin.service.IBlokService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "ortakIslemlerMB")
@ViewScoped
public class OrtakIslemlerMB implements Serializable {

    public static Logger logger = Logger.getLogger(OrtakIslemlerMB.class);
    //servisler
    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @PostConstruct
    public void init() {
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
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
}