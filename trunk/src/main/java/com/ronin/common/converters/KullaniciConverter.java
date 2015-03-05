package com.ronin.common.converters;

import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IKullaniciService;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.constant.Durum;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 28.03.2014
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@RequestScoped
public class KullaniciConverter implements Converter {

    @ManagedProperty("#{kullaniciService}")
    public IKullaniciService kullaniciService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        try {
            Long entityId = new Long(newValue);
            return kullaniciService.getKullaniciById(entityId);
        } catch (Throwable ex) {

            throw new ConverterException(ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String val = null;
        try {
            Kullanici kullanici = (Kullanici) value;
            val = Long.toString(kullanici.getId());
        } catch (Throwable ex) {
            throw new ConverterException("");
        }
        return val;
    }

    public IKullaniciService getKullaniciService() {
        return kullaniciService;
    }

    public void setKullaniciService(IKullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }
}
