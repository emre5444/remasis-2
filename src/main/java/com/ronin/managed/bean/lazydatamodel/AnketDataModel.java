/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Anket;
import com.ronin.model.Duyuru;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class AnketDataModel extends ListDataModel<Anket> implements SelectableDataModel<Anket> {

    public AnketDataModel(List<Anket> data) {
      super(data);
    }


    @Override
    public Object getRowKey(Anket anket) {
        return anket.getId().toString();
    }


    @Override
    public Anket getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Anket anket : (List<Anket>)getWrappedData()) {
           if(anket.getId().toString().equals(rowKey))
           return anket;
       }
       return null;
    }

}
