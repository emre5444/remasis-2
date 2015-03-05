package com.ronin.model.converters;

import com.ronin.common.service.IOrtakService;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.Blok;

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
 * User: fcabi
 */

@ManagedBean
@RequestScoped
public class BildirimTipiConverter implements Converter {

    @ManagedProperty("#{ortakService}")
    public IOrtakService ortakService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue != null && !newValue.equals("Seçiniz")) {
            try {
                Long entityId = new Long(newValue);

                return ortakService.getEntityByClass(BildirimTipi.class , entityId);

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
                BildirimTipi entity = (BildirimTipi) value;
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
