package com.ronin.commmon.beans;

import com.ronin.common.model.*;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.Sirket;
import com.ronin.utils.DateUtils;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "sessionInfo")
@SessionScoped
public class SessionInfo implements Serializable {

    Kullanici kullanici;

    List<Rol> rolList;

    List<Yetki> yetkiList;

    private Sirket sirket;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    private Date lastLoginDate;

    @PostConstruct
    public void initial() {
        User user = null;
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        kullanici = kullaniciService.getKullaniciByUsername(user.getUsername());
        lastLoginTimeSetter(kullanici);
        rolList = getRolList();
        yetkiList = getYetkiList();
    }

    public List<Rol> getRolList() {
        List<Rol> rolList = new ArrayList<Rol>();
        Set<KullaniciRol> kullaniciRols = kullanici.getKullaniciRolList();
        for (KullaniciRol kr : kullaniciRols) {
            rolList.add(kr.getRol());
        }
        return rolList;
    }

    public List<Yetki> getYetkiList() {
        List<Yetki> yetkiList = new ArrayList<Yetki>();
        for (Rol r : rolList) {
            List<RolYetki> ry = r.getRolYetkiList();
            for (RolYetki rolYetki : ry) {
                yetkiList.add(rolYetki.getYetki());
            }
        }
        Set hashset = new HashSet(yetkiList);
        yetkiList = new ArrayList(hashset);
        return yetkiList;
    }

    public boolean isYetkili(String yetkiLink) {
        for (Yetki y : yetkiList) {
            if (y.getLink().equals(yetkiLink)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdminMi() {
        for (Rol r : rolList) {
            if (r.getSistemYoneticisiMi().isEvetMi()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOneDaire(){
        if(kullanici != null && kullanici.getKullaniciDaireList() != null && !isAdminMi()){
            return kullanici.getKullaniciDaireList().size() == 1;
        }
        return false;
    }

    public void sirketChanged() {
        initial();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("window.location.replace(window.location.href);");
    }

    private void lastLoginTimeSetter(Kullanici kullanici){
        if(kullanici == null || kullanici.getLastLogin() == null){
            setLastLoginDate(DateUtils.getNow());
        } else{
            setLastLoginDate(kullanici.getLastLogin());
        }
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
