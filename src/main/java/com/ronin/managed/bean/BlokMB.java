package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.BlokDataModel;
import com.ronin.model.Interfaces.IAbstractEntity;
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

@ManagedBean(name = "blokMB")
@ViewScoped
public class BlokMB implements Serializable {

    public static Logger logger = Logger.getLogger(BlokMB.class);
    //servisler
    @ManagedProperty("#{blokService}")
    private IBlokService blokService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    private Blok yeniBlok = new Blok();


    //sorgu kriterleri
    private BlokSorguKriteri sorguKriteri = new BlokSorguKriteri();


    //daire islemleri
    private Blok selected;
    private BlokDataModel dataModel;
    List<Blok> blokSorguSonucu;

    @PostConstruct
    public void init() {
    }

    public void getBlokListBySorguKriteri() {
        List<Blok> dataList = blokService.getListCriteriaForPaging(0, 20, sorguKriteri ,sessionInfo );
        blokSorguSonucu = dataList;
        dataModel = new BlokDataModel(dataList);

        if (dataList == null || dataList.size() <= 0) {
            JsfUtil.addWarnMessage(message.getString("error.sonuc.yok"));
        }
    }

    public void yeniBlokEkleme() {
        try {
            blokService.add(sessionInfo ,yeniBlok);
            getBlokListBySorguKriteri();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('blokEklePopup').hide()");
            JsfUtil.addSuccessMessage(message.getString("blok_ekleme_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public void update(Blok blok) {
        try {
            blokService.update(sessionInfo ,blok);
            getBlokListBySorguKriteri();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('blokGuncellemePopup').hide()");
            JsfUtil.addSuccessMessage(message.getString("islem_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public void delete() {
        try {
            blokService.delete(sessionInfo ,selected);
            getBlokListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("islem_basarili"));
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public IBlokService getBlokService() {
        return blokService;
    }

    public void setBlokService(IBlokService blokService) {
        this.blokService = blokService;
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

    public BlokSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(BlokSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public Blok getSelected() {
        return selected;
    }

    public void setSelected(Blok selected) {
        this.selected = selected;
    }

    public BlokDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(BlokDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public List<Blok> getBlokSorguSonucu() {
        return blokSorguSonucu;
    }

    public void setBlokSorguSonucu(List<Blok> blokSorguSonucu) {
        this.blokSorguSonucu = blokSorguSonucu;
    }

    public Blok getYeniBlok() {
        return yeniBlok;
    }

    public void setYeniBlok(Blok yeniBlok) {
        this.yeniBlok = yeniBlok;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
}