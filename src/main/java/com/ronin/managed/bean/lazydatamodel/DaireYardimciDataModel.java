/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.DaireArac;
import com.ronin.model.DaireYardimci;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class DaireYardimciDataModel extends ListDataModel<DaireYardimci> implements SelectableDataModel<DaireYardimci> {

    public DaireYardimciDataModel(List<DaireYardimci> data) {
      super(data);
    }

    @Override
    public Object getRowKey(DaireYardimci daireYardimci) {
        return daireYardimci.getId().toString();
    }

    @Override
    public DaireYardimci getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(DaireYardimci daireYardimci : (List<DaireYardimci>)getWrappedData()) {
           if(daireYardimci.getId().toString().equals(rowKey))
           return daireYardimci;
       }
       return null;
    }

}