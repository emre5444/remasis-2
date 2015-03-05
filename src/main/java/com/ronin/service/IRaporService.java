/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Borc;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.RaporSorguKriteri;
import com.ronin.model.sorguSonucu.R101SorguSonucu;
import com.ronin.model.sorguSonucu.R201SorguSonucu;

import java.util.List;

/**
 *
 * @author ealtun
 */
public interface IRaporService {

    List<R101SorguSonucu> getR101ListCriteriaForPaging(RaporSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    List<R201SorguSonucu> getR201ListCriteriaForPaging(RaporSorguKriteri sorguKriteri ,SessionInfo sessionInfo);

}
