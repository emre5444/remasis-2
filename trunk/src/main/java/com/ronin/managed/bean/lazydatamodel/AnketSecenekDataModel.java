/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.AnketKullanici;
import com.ronin.model.AnketSecim;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class AnketSecenekDataModel extends ListDataModel<AnketSecim> implements SelectableDataModel<AnketSecim> {

    public AnketSecenekDataModel(List<AnketSecim> data) {
      super(data);
    }

    @Override
    public Object getRowKey(AnketSecim anketSecim) {
        return anketSecim.getId().toString();
    }

    @Override
    public AnketSecim getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(AnketSecim anketSecim : (List<AnketSecim>)getWrappedData()) {
           if(anketSecim.getId().toString().equals(rowKey))
           return anketSecim;
       }
       return null;
    }

}