/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.constant.Belge;
import com.ronin.model.constant.BildirimTipi;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author ealtun
 */
public class BildirimTipiDataModel extends ListDataModel<BildirimTipi> implements SelectableDataModel<BildirimTipi> {

    public BildirimTipiDataModel(List<BildirimTipi> data) {
      super(data);
    }


    @Override
    public Object getRowKey(BildirimTipi data) {
        return data.getId().toString();
    }


    @Override
    public BildirimTipi getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(BildirimTipi data : (List<BildirimTipi>)getWrappedData()) {
           if(data.getId().toString().equals(rowKey))
           return data;
       }
       return null;
    }

}
