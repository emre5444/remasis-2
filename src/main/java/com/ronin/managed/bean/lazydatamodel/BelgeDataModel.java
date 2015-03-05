/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Duyuru;
import com.ronin.model.constant.Belge;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class BelgeDataModel extends ListDataModel<Belge> implements SelectableDataModel<Belge> {

    public BelgeDataModel(List<Belge> data) {
      super(data);
    }


    @Override
    public Object getRowKey(Belge data) {
        return data.getId().toString();
    }


    @Override
    public Belge getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Belge data : (List<Belge>)getWrappedData()) {
           if(data.getId().toString().equals(rowKey))
           return data;
       }
       return null;
    }

}
