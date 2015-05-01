/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Randevu;
import com.ronin.model.constant.RandevuTipi;

import java.util.Date;
import java.util.List;

public interface IRandevuService {

    List<Randevu> getRandevuList(RandevuTipi randevuTipi, Date startDate, Date endDate, SessionInfo sessionInfo);

    Long hasAktifRandevu(RandevuTipi randevuTipi, Date startDate, Date endDate, SessionInfo sessionInfo,Randevu randevu);

}
