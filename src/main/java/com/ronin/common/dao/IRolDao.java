/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.common.dao;


import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.common.model.Yetki;
import com.ronin.model.kriter.RolSorguKriteri;

import java.util.List;

/**
 *
 * @author msevim
 */
public interface IRolDao {
    
    Rol getSingle(Rol rol);
    
    Rol getSingleById(Long id);
    
    Rol getSingleOneByNamedQuery(String namedQuery,Object... parameters);
    
    List<Rol> getList(Rol rol);

    List<Rol> getRolListByKullanici(Kullanici kullanici , SessionInfo sessionInfo);
    
    List<Rol> getListByNamedQuery(String namedQuery,Object... parameters);

    List<Yetki> getAllYetkiList();

    public List<Rol> getAllRolList(SessionInfo sessionInfo);

    void updateRolYetki(List<Yetki> yetkiList , Rol rol);

    void updateKullaniciRol(List<Rol> rolList , Kullanici kullanici);
    
    int getCount(Rol rol);

    Rol add(Rol rol);

    void update(Rol rol);

    void delete(Rol rol);

    List<Rol> getListCriteriaForPaging(int first, int pageSize, RolSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    void relateUserWithRole(Kullanici kullanici);
}
