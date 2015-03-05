/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.KullaniciSirket;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author fcabi
 */
public class KullaniciSirketDataModel extends ListDataModel<KullaniciSirket> implements SelectableDataModel<KullaniciSirket> {

    public KullaniciSirketDataModel(List<KullaniciSirket> data) {
      super(data);
    }


    @Override
    public Object getRowKey(KullaniciSirket kullaniciSirket) {
        return kullaniciSirket.getId().toString();
    }


    @Override
    public KullaniciSirket getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(KullaniciSirket kullaniciSirket : (List<KullaniciSirket>)getWrappedData()) {
           if(kullaniciSirket.getId().toString().equals(rowKey))
           return kullaniciSirket;
       }
       return null;
    }

}
