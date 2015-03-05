package com.ronin.model.converters;

import com.ronin.model.constant.Banka;
import com.ronin.model.constant.Blok;

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

@FacesConverter("bankaConverter")
public class BankaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        Banka entity = null;
        try {
            entity =  new Banka();
            entity.setId(new Long(newValue));
        }catch(Throwable ex) {

            throw new ConverterException(ex);
        }
        return entity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String val = null;
        try {
            Banka entity = (Banka) value;
            val = Long.toString(entity.getId());
        }catch(Throwable ex) {
            throw new ConverterException("");
        }
        return val;
    }
}
