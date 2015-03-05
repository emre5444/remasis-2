/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.common.model.Rol;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 *
 * @author msevim
 */
public class RolDataModel extends ListDataModel<Rol> implements SelectableDataModel<Rol> {

    public RolDataModel(List<Rol> data) {
      super(data);
    }

    @Override
    public Object getRowKey(Rol rol) {
        return rol.getId().toString();
    }

    @Override
    public Rol getRowData(String rowKey) {
        if(getWrappedData() == null)
            return null;
       for(Rol rol : (List<Rol>)getWrappedData()) {
           if(rol.getId().toString().equals(rowKey))
           return rol;
       }
       return null;
    }
}
