/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.common.model.Kullanici;
import com.ronin.model.KullaniciDaire;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class KullaniciDaireDataModel extends ListDataModel<KullaniciDaire> implements SelectableDataModel<KullaniciDaire> {

    public KullaniciDaireDataModel(List<KullaniciDaire> data) {
      super(data);
    }

    @Override
    public Object getRowKey(KullaniciDaire kullaniciDaire) {
        return kullaniciDaire.getId().toString();
    }

    @Override
    public KullaniciDaire getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(KullaniciDaire kullaniciDaire : (List<KullaniciDaire>)getWrappedData()) {
           if(kullaniciDaire.getId().toString().equals(rowKey))
           return kullaniciDaire;
       }
       return null;
    }

}