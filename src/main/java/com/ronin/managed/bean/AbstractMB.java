/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean;

import javax.faces.bean.ViewScoped;

/**
 *
 * @author esimsek
 */
@ViewScoped
public class AbstractMB<T> {
    
    private T selected;
    private String backPage;

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
}
