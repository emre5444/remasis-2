/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.DaireSakin;
import com.ronin.model.KullaniciDaire;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class DaireSakinDataModel extends ListDataModel<DaireSakin> implements SelectableDataModel<DaireSakin> {

    public DaireSakinDataModel(List<DaireSakin> data) {
      super(data);
    }

    @Override
    public Object getRowKey(DaireSakin daireSakin) {
        return daireSakin.getId().toString();
    }

    @Override
    public DaireSakin getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireSakin daireSakin : (List<DaireSakin>)getWrappedData()) {
           if(daireSakin.getId().toString().equals(rowKey))
           return daireSakin;
       }
       return null;
    }

}