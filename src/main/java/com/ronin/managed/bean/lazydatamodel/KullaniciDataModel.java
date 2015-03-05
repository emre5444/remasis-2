/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.common.model.Kullanici;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class KullaniciDataModel extends ListDataModel<Kullanici> implements SelectableDataModel<Kullanici> {

    public KullaniciDataModel(List<Kullanici> data) {
      super(data);
    }

    @Override
    public Object getRowKey(Kullanici kullanici) {
        return kullanici.getId().toString();
    }

    @Override
    public Kullanici getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Kullanici kullanici : (List<Kullanici>)getWrappedData()) {
           if(kullanici.getId().toString().equals(rowKey))
           return kullanici;
       }
       return null;
    }

}