package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.KategoriDataModel;
import com.ronin.model.Kategori;
import com.ronin.model.kriter.KategoriSorguKriteri;
import com.ronin.service.IEnvanterService;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "kategoriMB")
@ViewScoped
public class KategoriMB implements Serializable {

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

    private KategoriSorguKriteri sorguKriteri = new KategoriSorguKriteri();

    private Kategori selected;

    private KategoriDataModel dataModel;

    private HashMap<Long , TreeNode> currentNodeMap = new HashMap<>();

    private Kategori yeniKategori = new Kategori();


    @PostConstruct
    public void init() {
    }

    public void getKategoriListBySorguKriteri() {
        List<Kategori> dataList = envanterService.getKategoriListBySorguKriteri(0, 100, sorguKriteri , sessionInfo);
        dataModel = new KategoriDataModel(dataList);
    }

    public void yeniKategoriEkleme() {
        try {
            envanterService.kategoriEkleme(sessionInfo, yeniKategori);
            JsfUtil.addSuccessMessage(message.getString("kategori_ekleme_basarili"));
            yeniKategori = new Kategori();
        } catch (Exception e) {
            JsfUtil.addFatalMessage(e.toString());
        }
    }


    public void deleteKategori(Kategori selectedKategori) {
        try {
            setSelected(selectedKategori);
            envanterService.kategoriSilme(sessionInfo, selected);
            getKategoriListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("kategori_silme_basarili"));
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

    public KategoriSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(KategoriSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public Kategori getSelected() {
        return selected;
    }

    public void setSelected(Kategori selected) {
        this.selected = selected;
    }

    public KategoriDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(KategoriDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public HashMap<Long, TreeNode> getCurrentNodeMap() {
        return currentNodeMap;
    }

    public void setCurrentNodeMap(HashMap<Long, TreeNode> currentNodeMap) {
        this.currentNodeMap = currentNodeMap;
    }

    public Kategori getYeniKategori() {
        return yeniKategori;
    }

    public void setYeniKategori(Kategori yeniKategori) {
        this.yeniKategori = yeniKategori;
    }
}
