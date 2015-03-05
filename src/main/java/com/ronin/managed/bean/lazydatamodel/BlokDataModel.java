/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.constant.Blok;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * @author fcabi
 */
public class BlokDataModel extends ListDataModel<Blok> implements SelectableDataModel<Blok> {

    public BlokDataModel(List<Blok> data) {
        super(data);
    }


    @Override
    public Object getRowKey(Blok blok) {
        return blok.getId().toString();
    }


    @Override
    public Blok getRowData(String rowKey) {
        if (getWrappedData() == null)
            return null;
        for (Blok blok : (List<Blok>) getWrappedData()) {
            if (blok.getId().toString().equals(rowKey))
                return blok;
        }
        return null;
    }

}
