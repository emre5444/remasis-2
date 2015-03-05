package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.Interfaces.IAbstractEntity;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "profilMB")
@ViewScoped
public class ProfilMB implements Serializable {

    public static Logger logger = Logger.getLogger(ProfilMB.class);

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    private String eskiSifre;

    private String yeniSifre;

    private Kullanici selected;

    private List<IAbstractEntity> durumList;



    @PostConstruct
    public void init() {
        durumList = ortakService.getListByNamedQuery("Durum.findAll");
    }

    public void updateKullanici() {
        Kullanici selectedKullanici = sessionInfo.getKullanici();
        if (selectedKullanici.getPassword().equals(ortakService.getMD5String(eskiSifre))) {
            try {
                selectedKullanici.setPassword(yeniSifre);
                kullaniciService.updateKullaniciWithPasword(selectedKullanici);
                JsfUtil.addSuccessMessage(message.getString("sifre_guncelleme_basarili"));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PF('sifreDegistirmePopup').hide()");
            } catch (Exception e) {
                logger.error(e.getStackTrace());
                JsfUtil.addSuccessMessage(e.toString());
            }
        } else {
            JsfUtil.addErrorMessage(message.getString("error_sifreler_farkli_olamaz"));
        }
    }

    public void update() {
        try {
                kullaniciService.updateKullanici(selected);
                JsfUtil.addSuccessMessage(message.getString("kullanici_guncelleme_basarili"));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PF('kullaniciGuncellemePopup').hide()");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage(e.toString());
        }
    }

    public String getYeniSifre() {
        return yeniSifre;
    }

    public void setYeniSifre(String yeniSifre) {
        this.yeniSifre = yeniSifre;
    }

    public String getEskiSifre() {
        return eskiSifre;
    }

    public void setEskiSifre(String eskiSifre) {
        this.eskiSifre = eskiSifre;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
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

    public Kullanici getSelected() {
        return selected;
    }

    public void setSelected(Kullanici selected) {
        this.selected = selected;
    }

    public List<IAbstractEntity> getDurumList() {
        return durumList;
    }

    public void setDurumList(List<IAbstractEntity> durumList) {
        this.durumList = durumList;
    }
}
