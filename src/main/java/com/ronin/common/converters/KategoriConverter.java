package com.ronin.common.converters;

import com.ronin.common.service.IOrtakService;
import com.ronin.model.Kategori;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


@ManagedBean
@RequestScoped
public class KategoriConverter implements Converter {

    @ManagedProperty("#{ortakService}")
    public IOrtakService ortakService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        Kategori entity = null;
        if (newValue != null && !newValue.equals("Seçiniz")) {
            try {
                Long entityId = new Long(newValue);

                return ortakService.getSingleKategoriEntity(entityId);

            } catch (Throwable ex) {

                throw new ConverterException(ex);
            }
        }
        return entity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String val = null;
        if (value != null) {
            try {
                Kategori entity = (Kategori) value;
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
