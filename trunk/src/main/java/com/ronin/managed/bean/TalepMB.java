package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.managed.bean.lazydatamodel.TalepDataModel;
import com.ronin.model.DaireBorc;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.ItirazTalebi;
import com.ronin.model.TalepDaire;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.BilgilendirmeTipi;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.kriter.TalepSorguKriteri;
import com.ronin.service.ITalepService;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "talepMB")
@ViewScoped
public class TalepMB implements Serializable {

    public static Logger logger = Logger.getLogger(TalepMB.class);
    //servisler
    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{talepService}")
    private ITalepService talepService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    //sorgu kriterleri
    private TalepSorguKriteri sorguKriteri = new TalepSorguKriteri();

    //daire islemleri
    private TalepDaire selected;
    private TalepDataModel dataModel;


    //bildirim tipi
    public BildirimTipi.ENUM bildirimTipi;

    //combolar
    private List<TalepTipi> talepTipiList;
    private List<IAbstractEntity> blokList;
    private List<IAbstractEntity> durumList;

    @PostConstruct
    public void init() {
        prepareCombos();
        setUserRolInfos();
        getFlushObjects();
        getDaireListBySorguKriteri();
    }

    public void prepareCombos() {
        talepTipiList = getTalepTipiByYetki();
        dataModel = null;
        sorguKriteri = new TalepSorguKriteri();
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket",sessionInfo);
        durumList = ortakService.getListByNamedQuery("Durum.findAll");
    }

    public void getFlushObjects() {
        TalepSorguKriteri sk = (TalepSorguKriteri) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("sorguKriteri");
        if (sk != null) {
            sorguKriteri = sk;
            getDaireListBySorguKriteri();
        }
    }

    public void getDaireListBySorguKriteri() {
        sorguKriteri.setTalepTipiList(talepTipiList);
        List<TalepDaire> dataList = talepService.getListCriteriaForPaging(0, 3000, sorguKriteri, sessionInfo);

        dataModel = new TalepDataModel(dataList);

        if (dataList == null || dataList.size() <= 0) {
            JsfUtil.addWarnMessage(message.getString("error.sonuc.yok"));
        }
    }

    public List<Kullanici> completePlayer(String query) {
        List<Kullanici> suggestions = ortakService.getKullaniciByName(query , sessionInfo);
        return suggestions;
    }

    public List<DaireBorc> getItirazEdilenDaireBorcAsList() {
        return talepService.getItirazEdilenDaireBorcAsList(((ItirazTalebi) selected.getTalep()).getDaireBorc());
    }

    public void setUserRolInfos() {
        if (!sessionInfo.isAdminMi()) {
            sorguKriteri.setKullanici(sessionInfo.getKullanici());
        }
    }

    public List<TalepTipi> getTalepTipiByYetki() {
        List<TalepTipi> talepTipiList = new ArrayList<>();

        if (sessionInfo.isYetkili("yetki_ariza_talep")) {
            talepTipiList.add(talepService.getSingleTalepTipiByEnum(TalepTipi.ENUM.ARIZA_TALEBI));
        }
        if (sessionInfo.isYetkili("yetki_aidat_itiraz_talep")) {
            talepTipiList.add(talepService.getSingleTalepTipiByEnum(TalepTipi.ENUM.ITIRAZ_TALEBI));
        }
        if (sessionInfo.isYetkili("yetki_sikayet_talep")) {
            talepTipiList.add(talepService.getSingleTalepTipiByEnum(TalepTipi.ENUM.SIKAYET_TALEBI));
        }
        if (sessionInfo.isYetkili("yetki_belge_talep")) {
            talepTipiList.add(talepService.getSingleTalepTipiByEnum(TalepTipi.ENUM.BELGE_TALEBI));
        }

        return talepTipiList;
    }

    public void talepIptalEt() {
        chectSelectedItem();
        if (!selected.getTalep().getTalepOnayDurumu().isTalepOnayGormusMu()) {
            try {
                talepService.deleteTalep(selected);
                bildirimGonderForIptal();
                JsfUtil.addSuccessMessage(message.getString("talep_silme_basarili"));
                getDaireListBySorguKriteri();
            } catch (Exception e) {
                logger.error(e.getStackTrace());
                JsfUtil.addFatalMessage(e.toString());
            }
        } else {
            throw new ServiceException(message.getString("talep_onay_red_gormus_onay"));
        }
    }

    public void bildirimGonderForIptal() {
        if (selected.getTalep().getTalepTipi().isArizaTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.ARIZA;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("ariza_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("ariza_talebi_iptal_bildirim"), BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("ariza_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("ariza_talebi_iptal_bildirim"), BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isItirazTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.AIDAT_ITIRAZ;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("itiraz_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("itiraz_talebi_iptal_bildirim"),BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("itiraz_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("itiraz_talebi_iptal_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isSikayetTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.SIKAYET;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("sikayet_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("sikayet_talebi_iptal_bildirim"),BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("sikayet_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("sikayet_talebi_iptal_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        } else if (selected.getTalep().getTalepTipi().isBelgeTalebiMi()) {
            bildirimTipi = BildirimTipi.ENUM.BELGE_TALEBI;
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("belge_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("belge_talebi_iptal_bildirim"),BilgilendirmeTipi.ENUM.Email);
            ortakService.bildirimIstekOlustur(sessionInfo, kullaniciService.getKullaniciByUsername(selected.getTalep().getKullanici().getUsername()), bildirimTipi, selected.getTalep().getId() + message.getString("belge_talebi_iptal_bildirim"), selected.getTalep().getId() + message.getString("belge_talebi_iptal_bildirim"),BilgilendirmeTipi.ENUM.Notification);
        }
    }

    public boolean chectSelectedItem() {
        if (selected == null) {
            JsfUtil.addErrorMessage(message.getString("error_kayit_secilmedi"));
            return false;
        }
        return true;
    }

    //page navigations
    public String talepGoruntuleme() {
        if (chectSelectedItem()) {
            storeFlashObjects();
            return "talepGoruntuleme.xhtml";
        }
        return "";
    }

    public String talepOnaylama() {
        if (chectSelectedItem()) {
            if (!selected.getTalep().getTalepOnayDurumu().isTalepOnayGormusMu()) {
                storeFlashObjects();
                return "talepOnaylama.xhtml";
            } else {
                JsfUtil.addErrorMessage(message.getString("talep_onay_red_gormus_onay"));
            }
        }
        return "";
    }

    public String talepReddetme() {
        if (chectSelectedItem()) {
            if (!selected.getTalep().getTalepOnayDurumu().isTalepOnayGormusMu()) {
                storeFlashObjects();
                return "talepReddetme.xhtml";
            } else {
                JsfUtil.addErrorMessage(message.getString("talep_onay_red_gormus_onay"));
            }
        }
        return "";
    }

    public void storeFlashObjects() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedTalepDaireObject", selected);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sorguKriteri", sorguKriteri);
    }

    //getter and setters
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

    public ITalepService getTalepService() {
        return talepService;
    }

    public void setTalepService(ITalepService talepService) {
        this.talepService = talepService;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public TalepSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(TalepSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public TalepDaire getSelected() {
        return selected;
    }

    public void setSelected(TalepDaire selected) {
        this.selected = selected;
    }

    public TalepDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(TalepDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public List<TalepTipi> getTalepTipiList() {
        return talepTipiList;
    }

    public void setTalepTipiList(List<TalepTipi> talepTipiList) {
        this.talepTipiList = talepTipiList;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public List<IAbstractEntity> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<IAbstractEntity> blokList) {
        this.blokList = blokList;
    }

    public List<IAbstractEntity> getDurumList() {
        return durumList;
    }

    public void setDurumList(List<IAbstractEntity> durumList) {
        this.durumList = durumList;
    }
}
