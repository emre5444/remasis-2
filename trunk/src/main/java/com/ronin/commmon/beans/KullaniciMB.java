package com.ronin.commmon.beans;

import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.DurumEnum;
import com.ronin.utils.web.JsfUtil;
import org.springframework.dao.DataAccessException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@ManagedBean(name = "kullaniciMB")
@RequestScoped
public class KullaniciMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String BACK = "back";

    @ManagedProperty(value = "#{kullaniciService}")
    IKullaniciService kullaniciService;
    List<Kullanici> kullaniciList;
    private int id;
    private String ad;
    private String soyad;

    private Durum durum;
    private List<DurumEnum> durumList;

    public KullaniciMB() {
        durum = Durum.getAktifObject();
        
        durumList = new ArrayList<DurumEnum>();
        durumList.addAll(Arrays.asList(DurumEnum.values()));
    }

    public void addKullanici() {
        try {
            Kullanici user = new Kullanici();
            //	user.setId(getId());
            user.setAd(getAd());
            user.setSoyad(getSoyad());
            user.setUsername("deneme");
            user.setPassword("deneme");
            user.setDurum(durum);
            getKullaniciService().addKullanici(user);
            
            kullaniciList=kullaniciService.getKullaniciList();
            
            JsfUtil.addSuccessMessage("Kullanici ekleme isleminiz basari ile tamamlanmistir.");
            //return SUCCESS;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        //return ERROR;
    }

    public void reset() {
        this.setId(0);
        this.setAd("");
        this.setSoyad("");
    }

    public String backLink() {
        return BACK;
    }

    public List<Kullanici> getKullaniciList() {
        kullaniciList = new ArrayList<Kullanici>();
        kullaniciList.addAll(getKullaniciService().getKullaniciList());
        return kullaniciList;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public void setUserList(List<Kullanici> userList) {
        this.kullaniciList = userList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public List<DurumEnum> getDurumList() { return durumList; }

    public void setDurumList(List<DurumEnum> durumList) {
        this.durumList = durumList;
    }
  
}