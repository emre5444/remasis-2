/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.DaireArac;
import com.ronin.model.DaireHayvan;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class DaireHayvanDataModel extends ListDataModel<DaireHayvan> implements SelectableDataModel<DaireHayvan> {

    public DaireHayvanDataModel(List<DaireHayvan> data) {
      super(data);
    }

    @Override
    public Object getRowKey(DaireHayvan daireHayvan) {
        return daireHayvan.getId().toString();
    }

    @Override
    public DaireHayvan getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireHayvan daireHayvan : (List<DaireHayvan>)getWrappedData()) {
           if(daireHayvan.getId().toString().equals(rowKey))
           return daireHayvan;
       }
       return null;
    }

}