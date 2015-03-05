package com.ronin.model.converters;

import com.ronin.common.service.IOrtakService;
import com.ronin.model.constant.BorcTipi;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


@ManagedBean
@RequestScoped
public class BorcTipiConverter implements Converter {

    @ManagedProperty("#{ortakService}")
    public IOrtakService ortakService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue != null && !newValue.equals("Seçiniz")) {
            try {
                Long entityId = new Long(newValue);

                return ortakService.getEntityByClass(BorcTipi.class, entityId);

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
                BorcTipi entity = (BorcTipi) value;
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
