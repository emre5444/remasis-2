/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.TalepDaire;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class TalepDataModel extends ListDataModel<TalepDaire> implements SelectableDataModel<TalepDaire> {

    public TalepDataModel(List<TalepDaire> data) {
      super(data);
    }


    @Override
    public Object getRowKey(TalepDaire talepDaire) {
        return talepDaire.getId().toString();
    }


    @Override
    public TalepDaire getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(TalepDaire talepDaire : (List<TalepDaire>)getWrappedData()) {
           if(talepDaire.getId().toString().equals(rowKey))
           return talepDaire;
       }
       return null;
    }

}
