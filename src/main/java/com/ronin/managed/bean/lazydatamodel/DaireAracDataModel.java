/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.DaireArac;
import com.ronin.model.DaireSakin;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class DaireAracDataModel extends ListDataModel<DaireArac> implements SelectableDataModel<DaireArac> {

    public DaireAracDataModel(List<DaireArac> data) {
      super(data);
    }

    @Override
    public Object getRowKey(DaireArac daireArac) {
        return daireArac.getId().toString();
    }

    @Override
    public DaireArac getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireArac daireArac : (List<DaireArac>)getWrappedData()) {
           if(daireArac.getId().toString().equals(rowKey))
           return daireArac;
       }
       return null;
    }

}