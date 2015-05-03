/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.common.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IRolDao;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.common.model.Yetki;
import com.ronin.model.kriter.RolSorguKriteri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author msevim
 */
@Service(value = "rolService")
@Transactional(readOnly =true, propagation = Propagation.SUPPORTS)
public class RolService implements IRolService {
    
    @Autowired
    IRolDao iRolDao;

    @Transactional(readOnly = false)
    public Rol getSingle(Rol rol) {
        return iRolDao.getSingle(rol);
    }

    @Transactional(readOnly = false)
    public Rol getSingleById(Long id) {
        return iRolDao.getSingleById(id);
    }

    @Transactional(readOnly = false)
    public Rol getSingleOneByNamedQuery(String namedQuery,Object... parameters) {
        return iRolDao.getSingleOneByNamedQuery(namedQuery,parameters);
    }

    @Transactional(readOnly = false)
    public List<Rol> getList(Rol rol) {
        return iRolDao.getList(rol);
    }

    @Transactional(readOnly = false)
    public List<Rol> getRolListByKullanici(Kullanici kullanici , SessionInfo sessionInfo){
        return iRolDao.getRolListByKullanici(kullanici , sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Rol> getRolListByKullanici(Kullanici kullanici){
        return iRolDao.getRolListByKullanici(kullanici);
    }

    @Transactional(readOnly = false)
    public List<Rol> getListByNamedQuery(String namedQuery,Object... parameters) {
        return iRolDao.getListByNamedQuery(namedQuery,parameters);
    }

    @Transactional(readOnly = false)
    public List<Rol> getListCriteriaForPaging(int first, int pageSize, RolSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        return iRolDao.getListCriteriaForPaging(first, pageSize, sorguKriteri , sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Yetki> getAllYetkiList(){
        return iRolDao.getAllYetkiList();
    }

    @Transactional(readOnly = false)
    public List<Rol> getAllRolList(SessionInfo sessionInfo){
        return iRolDao.getAllRolList(sessionInfo);
    }

    @Transactional(readOnly = false)
    public void updateRolYetki(List<Yetki> yetkiList , Rol rol){
         iRolDao.updateRolYetki(yetkiList , rol);
    }

    @Transactional(readOnly = false)
    public void updateKullaniciRol(List<Rol> rolList , Kullanici kullanici){
        iRolDao.updateKullaniciRol(rolList , kullanici);
    }

    @Transactional(readOnly = false)
    public int getCount(Rol rol) {
        return iRolDao.getCount(rol);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Rol add(Rol rol) {
        return iRolDao.add(rol);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Rol rol) {
        iRolDao.update(rol);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Rol rol) {
        iRolDao.delete(rol);
    }
}
