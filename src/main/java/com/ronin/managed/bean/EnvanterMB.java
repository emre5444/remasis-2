package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.EnvanterDataModel;
import com.ronin.model.Envanter;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.EnvanterSorguKriteri;
import com.ronin.service.IEnvanterService;
import com.ronin.utils.DateUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "envanterMB")
@ViewScoped
public class EnvanterMB implements Serializable {

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{envanterService}")
    private IEnvanterService envanterService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    private EnvanterSorguKriteri sorguKriteri = new EnvanterSorguKriteri();

    private Envanter selected;

    private EnvanterDataModel dataModel;

    private Envanter yeniEnvanter = new Envanter();

    private List<IAbstractEntity> kategoriList;

    @PostConstruct
    public void init() {
        kategoriList = ortakService.getListByNamedQuery("Kategori.findAll");
    }

    public void getEnvanterListBySorguKriteri() {
        List<Envanter> dataList = envanterService.getEnvanterListBySorguKriteri(0, 100, sorguKriteri, sessionInfo);
        dataModel = new EnvanterDataModel(dataList);
    }

    public void yeniEnvanterEkleme() {
        try {
            yeniEnvanter.setKullanici(sessionInfo.getKullanici());
            yeniEnvanter.setTanitimZamani(DateUtils.getToday());
            yeniEnvanter.setDurum(Durum.getAktifObject());
            yeniEnvanter.setSirket(sessionInfo.getSirket());
            yeniEnvanter.setBarkodNo(generateBarkodNo(8));
            envanterService.save(yeniEnvanter);
            JsfUtil.addSuccessMessage(message.getString("envanter_ekleme_basarili"));

        } catch (Exception e) {
            JsfUtil.addFatalMessage(e.toString());
        }
    }


    public void deleteEnvanter() {
        try {
            envanterService.envanterSilme(sessionInfo, selected);
            getEnvanterListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("envanter_silme_basarili"));
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("Hata!");
        }

    }

    public void updateEnvanter() {
        try {
            envanterService.envanterGuncelleme(sessionInfo, selected);
            getEnvanterListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("envanter_guncelleme_basarili"));
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("Hata!");
        }

    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public IEnvanterService getEnvanterService() {
        return envanterService;
    }

    public void setEnvanterService(IEnvanterService envanterService) {
        this.envanterService = envanterService;
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

    public EnvanterSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(EnvanterSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public Envanter getSelected() {
        return selected;
    }

    public void setSelected(Envanter selected) {
        this.selected = selected;
    }

    public EnvanterDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(EnvanterDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Envanter getYeniEnvanter() {
        return yeniEnvanter;
    }

    public void setYeniEnvanter(Envanter yeniEnvanter) {
        this.yeniEnvanter = yeniEnvanter;
    }

    public List<IAbstractEntity> getKategoriList() {
        return kategoriList;
    }

    public void setKategoriList(List<IAbstractEntity> kategoriList) {
        this.kategoriList = kategoriList;
    }

    private String generateBarkodNo(int lenght) {
        String barkodNo;
        do {
            barkodNo = envanterService.barkodNoGenerator(lenght);
        }
        while (envanterService.getEnvanterByBarkodNo(barkodNo) != null);
        return barkodNo;
    }

    public String getHostAdress(){
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
