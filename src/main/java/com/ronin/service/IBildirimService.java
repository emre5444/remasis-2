/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.model.kriter.HedefKitle;

import java.util.List;

/**
 *
 * @author msevim
 */
public interface IBildirimService {

    BildirimTipi getSingle(BildirimTipi bildirimTipi);

    BildirimTipi getSingleById(Long id);

    List<BildirimTipi> getList(BildirimTipi bildirimTipi);

    List<Rol> getRolListByBildirimTipi(BildirimTipi bildirimTipi , SessionInfo sessionInfo);

    List<Kullanici> getKullaniciListForBildirim(HedefKitle hedefKitle , SessionInfo sessionInfo);

    List<BildirimTipi> getListCriteriaForPaging(int first, int pageSize, BildirimTipiSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    List<BildirimTipi> getAllBildirimTipiList();

    void updateBldirimTipiRol(List<Rol> rolList, BildirimTipi bildirimTipi ,SessionInfo sessionInfo);

    List<Rol> getAllRolList(SessionInfo sessionInfo);

}
