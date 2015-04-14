/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean;

import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.model.enums.Sorun;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ResourceBundle;

/**
 *
 * @author esimsek
 */
@ViewScoped
public class AbstractMB<T> {

    @ManagedProperty("#{msg}")
    private ResourceBundle message;
    
    private T selected;
    private String backPage;


    public void handleSorun(Sorun sorun){
        if(sorun.equals(Sorun.MALIZ_ZATEN_VAR)){
            JsfUtil.addSuccessMessage(message.getString("error.dairede.birden.fazla.malik.olamaz"));
        } else   if(sorun.equals(Sorun.SAKIN_ZATEN_VAR)){
            JsfUtil.addSuccessMessage(message.getString("error.dairede.birden.fazla.sakin.olamaz"));
        }
    }




    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public String getBackPage() {
        return backPage;
    }

    public void setBackPage(String backPage) {
        this.backPage = backPage;
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }
}
