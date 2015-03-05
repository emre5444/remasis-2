/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Daire;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.service.IDaireService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.model.ListDataModel;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ealtun
 */
public class DaireDataModel  extends ListDataModel<Daire> implements SelectableDataModel<Daire> {

    public DaireDataModel( List<Daire> data) {
      super(data);
    }


    @Override
    public Object getRowKey(Daire daire) {
        return daire.getId().toString();
    }


    @Override
    public Daire getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Daire daire : (List<Daire>)getWrappedData()) {
           if(daire.getId().toString().equals(rowKey))
           return daire;
       }
       return null;
    }

}
