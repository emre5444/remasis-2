/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Borc;
import com.ronin.model.DaireBorcKalem;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class DaireBorcKalemDataModel extends ListDataModel<DaireBorcKalem> implements SelectableDataModel<DaireBorcKalem> {

    public DaireBorcKalemDataModel(List<DaireBorcKalem> data) {
      super(data);
    }


    @Override
    public Object getRowKey(DaireBorcKalem borc) {
        return borc.getId().toString();
    }


    @Override
    public DaireBorcKalem getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireBorcKalem borc : (List<DaireBorcKalem>)getWrappedData()) {
           if(borc.getId().toString().equals(rowKey))
           return borc;
       }
       return null;
    }

}
