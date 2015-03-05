/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Kategori;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class KategoriDataModel extends ListDataModel<Kategori> implements SelectableDataModel<Kategori> {

    public KategoriDataModel(List<Kategori> data) {
      super(data);
    }

    @Override
    public Object getRowKey(Kategori kategori) {
        return kategori.getId().toString();
    }

    @Override
    public Kategori getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Kategori kategori : (List<Kategori>)getWrappedData()) {
           if(kategori.getId().toString().equals(rowKey))
           return kategori;
       }
       return null;
    }
}
