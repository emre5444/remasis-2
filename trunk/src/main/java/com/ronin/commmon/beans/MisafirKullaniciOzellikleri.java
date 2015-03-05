package com.ronin.commmon.beans;

import org.springframework.context.MessageSource;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

@ManagedBean(name = "misafirKullaniciOzellikleri")
@SessionScoped
public class MisafirKullaniciOzellikleri implements Serializable {

    @ManagedProperty(value = "#{labels}")
    private MessageSource lbl;

    @ManagedProperty(value = "#{labels}")
    private MessageSource msg;

    public String locale;
    public SelectItem[] countries;
    private String theme = "bootstrap";

    public Locale kullaniciLocale;

    public String getTheme() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        if (params.containsKey("theme")) {
            theme = params.get("theme");
        }
        return theme;
    }

    @PostConstruct
    public void initial() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        kullaniciLocale = new Locale("tr", "TR");
        countries = new SelectItem[2];
        countries[0] = new SelectItem("tr", lbl.getMessage("turkish", null, kullaniciLocale));
        countries[1] = new SelectItem("en", lbl.getMessage("english", null, kullaniciLocale));
    }

    public void localeChanged(ValueChangeEvent e) {
        String newLocaleValue = e.getNewValue().toString();
        if (newLocaleValue.equals("tr")) {
            kullaniciLocale = new Locale("tr", "TR");
        } else if (newLocaleValue.equals("en")) {
            kullaniciLocale = Locale.US;
        }
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(kullaniciLocale);
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public SelectItem[] getCountries() {
        return countries;
    }

    public void setCountries(SelectItem[] countries) {
        this.countries = countries;
    }

    public MessageSource getLbl() {
        return lbl;
    }

    public void setLbl(MessageSource lbl) {
        this.lbl = lbl;
    }

    public MessageSource getMsg() {
        return msg;
    }

    public void setMsg(MessageSource msg) {
        this.msg = msg;
    }

    public Locale getKullaniciLocale() {
        return kullaniciLocale;
    }

    public void setKullaniciLocale(Locale kullaniciLocale) {
        this.kullaniciLocale = kullaniciLocale;
    }

}
