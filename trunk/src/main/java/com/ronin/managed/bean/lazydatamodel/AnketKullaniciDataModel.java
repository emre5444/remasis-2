/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.AnketKullanici;
import com.ronin.model.KullaniciDaire;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class AnketKullaniciDataModel extends ListDataModel<AnketKullanici> implements SelectableDataModel<AnketKullanici> {

    public AnketKullaniciDataModel(List<AnketKullanici> data) {
      super(data);
    }

    @Override
    public Object getRowKey(AnketKullanici anketKullanici) {
        return anketKullanici.getId().toString();
    }

    @Override
    public AnketKullanici getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(AnketKullanici anketKullanici : (List<AnketKullanici>)getWrappedData()) {
           if(anketKullanici.getId().toString().equals(rowKey))
           return anketKullanici;
       }
       return null;
    }

}