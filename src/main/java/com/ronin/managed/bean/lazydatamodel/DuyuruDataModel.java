/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Daire;
import com.ronin.model.Duyuru;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class DuyuruDataModel extends ListDataModel<Duyuru> implements SelectableDataModel<Duyuru> {

    public DuyuruDataModel(List<Duyuru> data) {
      super(data);
    }


    @Override
    public Object getRowKey(Duyuru daire) {
        return daire.getId().toString();
    }


    @Override
    public Duyuru getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Duyuru daire : (List<Duyuru>)getWrappedData()) {
           if(daire.getId().toString().equals(rowKey))
           return daire;
       }
       return null;
    }

}
