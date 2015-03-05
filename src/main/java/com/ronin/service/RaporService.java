/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.dao.api.IDaireDao;
import com.ronin.dao.api.IRaporDao;
import com.ronin.model.Borc;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.RaporSorguKriteri;
import com.ronin.model.sorguSonucu.R101SorguSonucu;
import com.ronin.model.sorguSonucu.R201SorguSonucu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esimsek
 */
@Service(value = "raporService")
@Transactional(readOnly =true, propagation = Propagation.SUPPORTS)
public class RaporService implements IRaporService {
    
    @Autowired
    IRaporDao iRaporDao;


    @Transactional(readOnly = false)
    public List<R101SorguSonucu> getR101ListCriteriaForPaging(RaporSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        return iRaporDao.getR101ListCriteriaForPaging(sorguKriteri , sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<R201SorguSonucu> getR201ListCriteriaForPaging(RaporSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        return iRaporDao.getR201ListCriteriaForPaging(sorguKriteri , sessionInfo);
    }


    public IRaporDao getiRaporDao() {
        return iRaporDao;
    }

    public void setiRaporDao(IRaporDao iRaporDao) {
        this.iRaporDao = iRaporDao;
    }
}
