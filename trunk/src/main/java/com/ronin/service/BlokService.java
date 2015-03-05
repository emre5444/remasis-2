/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.dao.api.IBlokDao;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.BlokSorguKriteri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fcabi
 */
@Service(value = "blokService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BlokService implements IBlokService {

    @Autowired
    IBlokDao iBlokDao;


    @Transactional(readOnly = false)
    public List<Blok> getListCriteriaForPaging(int first, int pageSize, BlokSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        return iBlokDao.getListCriteriaForPaging(first, pageSize, sorguKriteri , sessionInfo);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void add(SessionInfo sessionInfo ,Blok blok) {
        blok.setDurum(Durum.getAktifObject());
        iBlokDao.add(sessionInfo,blok);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(SessionInfo sessionInfo ,Blok blok) {
        iBlokDao.update(sessionInfo ,blok);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(SessionInfo sessionInfo ,Blok blok) {
        iBlokDao.delete(sessionInfo ,blok);
    }

}
