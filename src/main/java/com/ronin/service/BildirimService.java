/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IRolDao;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.dao.api.IBildirimDao;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.model.kriter.HedefKitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author msevim
 */
@Service(value = "bildirimService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BildirimService implements IBildirimService {

    @Autowired
    IRolDao iRolDao;

    @Autowired
    IBildirimDao iBildirimDao;

    @Transactional(readOnly = false)
    public BildirimTipi getSingle(BildirimTipi bildirimTipi) {
        return iBildirimDao.getSingle(bildirimTipi);
    }

    @Transactional(readOnly = false)
    public BildirimTipi getSingleById(Long id) {
        return iBildirimDao.getSingleById(id);
    }

    @Transactional(readOnly = false)
    public List<BildirimTipi> getList(BildirimTipi bildirimTipi) {
        return iBildirimDao.getList(bildirimTipi);
    }

    @Transactional(readOnly = false)
    public List<Rol> getRolListByBildirimTipi(BildirimTipi bildirimTipi, SessionInfo sessionInfo) {
        return iBildirimDao.getRolListByBildirimTipi(bildirimTipi, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Kullanici> getKullaniciListForBildirim(HedefKitle hedefKitle, SessionInfo sessionInfo) {
        return iBildirimDao.getKullaniciListForBildirim(hedefKitle, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<BildirimTipi> getListCriteriaForPaging(int first, int pageSize, BildirimTipiSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        return iBildirimDao.getListCriteriaForPaging(first, pageSize, sorguKriteri, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<BildirimTipi> getAllBildirimTipiList() {
        return iBildirimDao.getAllBildirimTipiList();
    }

    @Transactional(readOnly = false)
    public void updateBldirimTipiRol(List<Rol> rolList, BildirimTipi bildirimTipi, SessionInfo sessionInfo) {
        iBildirimDao.updateBldirimTipiRol(rolList, bildirimTipi, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Rol> getAllRolList(SessionInfo sessionInfo) {
        return iRolDao.getAllRolList(sessionInfo);
    }

    public IRolDao getiRolDao() {
        return iRolDao;
    }

    public void setiRolDao(IRolDao iRolDao) {
        this.iRolDao = iRolDao;
    }

    public IBildirimDao getiBildirimDao() {
        return iBildirimDao;
    }

    public void setiBildirimDao(IBildirimDao iBildirimDao) {
        this.iBildirimDao = iBildirimDao;
    }
}
