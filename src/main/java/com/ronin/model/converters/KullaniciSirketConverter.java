package com.ronin.model.converters;

import com.ronin.common.service.IOrtakService;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.Sirket;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 28.03.2014
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@RequestScoped
public class KullaniciSirketConverter implements Converter {

    @ManagedProperty("#{ortakService}")
    public  IOrtakService ortakService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue != null && !newValue.equals("Seçiniz")) {
            try {
                Long entityId = new Long(newValue);

                return ortakService.getSingleKullaniciSirketEntity(entityId);

            } catch (Throwable ex) {

                throw new ConverterException(ex);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String val = null;
        if (value != null) {
            try {
                KullaniciSirket entity = (KullaniciSirket) value;
                val = Long.toString(entity.getId());
            } catch (Throwable ex) {
                throw new ConverterException("");
            }
        }
        return val;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }
}
