package com.ronin.managed.bean;

import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.SifreHatirlatma;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "loginMB")
@ViewScoped
public class LoginMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{kullaniciService}")
    private IKullaniciService kullaniciService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    private String userName = null;
    private String password = null;

    private List<String> images;

    Integer numOfSirket;
    private String email;

    @PostConstruct
    public void init() {
        prepareInfors();
    }

    public void prepareInfors() {
        numOfSirket = ortakService.numberOfSirket();
        images = new ArrayList<String>();
        for (int i = 1; i <= numOfSirket; i++) {
            images.add("sirket" + i + ".png");
        }
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // This is the action method called when the user clicks the "login" button
    public String doLogin() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    public void sifreHatirlatmaIstekGonder() {
        Kullanici kullanici = kullaniciService.getKullaniciByEmail(email);
        if (kullanici != null) {
            long bekleyenIslemMi = kullaniciService.getBekleyenSifreIslemi(kullanici.getId());
            if (bekleyenIslemMi > 0) {
                JsfUtil.addWarnMessage(message.getString("login_sifremi_unuttum_duplicate"));
                return;
            }
            SifreHatirlatma sifreHatirlatma = new SifreHatirlatma();
            sifreHatirlatma.setKullanici(kullanici);
            sifreHatirlatma.setIslemTarihi(new Timestamp(new Date().getTime()));

            kullaniciService.sifreHatirlatmaIstekGonder(sifreHatirlatma);
            JsfUtil.addSuccessMessage(message.getString("login_sifre_hatirlatma_basarili"));
        } else {
            JsfUtil.addErrorMessage(message.getString("login_sifre_hatirlatma_basarisiz"));
        }
    }
}
