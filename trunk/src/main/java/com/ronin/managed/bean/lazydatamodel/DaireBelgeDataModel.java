/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.DaireBelge;
import com.ronin.model.constant.Belge;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class DaireBelgeDataModel extends ListDataModel<DaireBelge> implements SelectableDataModel<DaireBelge> {

    public DaireBelgeDataModel(List<DaireBelge> data) {
      super(data);
    }


    @Override
    public Object getRowKey(DaireBelge data) {
        return data.getId().toString();
    }


    @Override
    public DaireBelge getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireBelge data : (List<DaireBelge>)getWrappedData()) {
           if(data.getId().toString().equals(rowKey))
           return data;
       }
       return null;
    }

}
