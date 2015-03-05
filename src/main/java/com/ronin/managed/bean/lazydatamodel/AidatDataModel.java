/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.DaireBorc;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * @author fcabi
 */
public class AidatDataModel extends ListDataModel<DaireBorc> implements SelectableDataModel<DaireBorc> {

    public AidatDataModel(List<DaireBorc> data) {
        super(data);
    }


    @Override
    public Object getRowKey(DaireBorc daireBorc) {
        return daireBorc.getId().toString();
    }


    @Override
    public DaireBorc getRowData(String rowKey) {
        if (getWrappedData() == null)
            return null;
        for (DaireBorc daireBorc : (List<DaireBorc>) getWrappedData()) {
            if (daireBorc.getId().toString().equals(rowKey))
                return daireBorc;
        }
        return null;
    }

}
