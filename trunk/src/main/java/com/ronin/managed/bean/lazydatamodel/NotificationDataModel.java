/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.managed.bean.lazydatamodel;

import com.ronin.model.Notification;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * @author fcabi
 */
public class NotificationDataModel extends ListDataModel<Notification> implements SelectableDataModel<Notification> {

    public NotificationDataModel(List<Notification> data) {
        super(data);
    }


    @Override
    public Object getRowKey(Notification notification) {
        return notification.getId().toString();
    }


    @Override
    public Notification getRowData(String rowKey) {
        if (getWrappedData() == null)
            return null;
        for (Notification notification : (List<Notification>) getWrappedData()) {
            if (notification.getId().toString().equals(rowKey))
                return notification;
        }
        return null;
    }

}
