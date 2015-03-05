/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Envanter;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class EnvanterDataModel extends ListDataModel<Envanter> implements SelectableDataModel<Envanter> {

    public EnvanterDataModel(List<Envanter> data) {
      super(data);
    }

    @Override
    public Object getRowKey(Envanter envanter) {
        return envanter.getId().toString();
    }

    @Override
    public Envanter getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Envanter envanter : (List<Envanter>)getWrappedData()) {
           if(envanter.getId().toString().equals(rowKey))
           return envanter;
       }
       return null;
    }
}
