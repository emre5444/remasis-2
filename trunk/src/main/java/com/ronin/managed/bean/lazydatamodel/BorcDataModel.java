/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Borc;
import com.ronin.model.DaireBorc;
import com.ronin.model.Duyuru;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class BorcDataModel extends ListDataModel<DaireBorc> implements SelectableDataModel<DaireBorc> {

    public BorcDataModel(List<DaireBorc> data) {
      super(data);
    }


    @Override
    public Object getRowKey(DaireBorc borc) {
        return borc.getId().toString();
    }


    @Override
    public DaireBorc getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireBorc borc : (List<DaireBorc>)getWrappedData()) {
           if(borc.getId().toString().equals(rowKey))
           return borc;
       }
       return null;
    }

}
