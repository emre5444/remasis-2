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
import com.ronin.dao.api.IRandevuDao;
import com.ronin.model.Randevu;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.RandevuTipi;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.model.kriter.HedefKitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service(value = "randevuService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RandevuService implements IRandevuService {

    @Autowired
    IRandevuDao iRandevuDao;

    @Transactional(readOnly = false)
    public List<Randevu> getRandevuList(RandevuTipi randevuTipi, Date startDate, Date endDate , SessionInfo sessionInfo) {
        return iRandevuDao.getRandevuList(randevuTipi ,startDate , endDate , sessionInfo);
    }

    @Transactional(readOnly = false)
    public Long hasAktifRandevu(RandevuTipi randevuTipi, Date startDate, Date endDate, SessionInfo sessionInfo){
        return iRandevuDao.hasAktifRandevu(randevuTipi, startDate, endDate, sessionInfo);
    }


}
