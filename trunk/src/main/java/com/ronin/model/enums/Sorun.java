package com.ronin.model.enums;

import com.ronin.model.Interfaces.BaseEnum;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 14.04.2015
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public enum Sorun implements BaseEnum {
    MALIZ_ZATEN_VAR, SAKIN_ZATEN_VAR;

    private Long value;

    private Sorun() {

    }

    Sorun(Long val) {
        value = val;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return name();
    }

    public void setValue(Long value) {
        this.value = value;
    }


}
