/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;


import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.common.model.Yetki;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.model.kriter.RolSorguKriteri;

import java.util.List;

/**
 *
 * @author msevim
 */
public interface IBildirimDao {

    BildirimTipi getSingle(BildirimTipi bildirimTipi);

    BildirimTipi getSingleById(Long id);
    
    List<BildirimTipi> getList(BildirimTipi bildirimTipi);

    List<Rol> getRolListByBildirimTipi(BildirimTipi bildirimTipi , SessionInfo sessionInfo);

    List<BildirimTipi> getAllBildirimTipiList();

    void updateBldirimTipiRol(List<Rol> rolList, BildirimTipi bildirimTipi , SessionInfo sessionInfo);

    List<BildirimTipi> getListCriteriaForPaging(int first, int pageSize, BildirimTipiSorguKriteri sorguKriteri , SessionInfo sessionInfo);

}
