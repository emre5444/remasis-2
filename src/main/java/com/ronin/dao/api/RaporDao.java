/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Borc;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.DaireCriteria;
import com.ronin.model.criteria.R101RaporCriteria;
import com.ronin.model.criteria.R201RaporCriteria;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.RaporSorguKriteri;
import com.ronin.model.sorguSonucu.R101SorguSonucu;
import com.ronin.model.sorguSonucu.R201SorguSonucu;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author esimsek
 */
@Repository
public class RaporDao implements IRaporDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<R101SorguSonucu> getR101ListCriteriaForPaging(RaporSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        R101RaporCriteria criteria = new R101RaporCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession());
        return (List<R101SorguSonucu>) criteria.prepareResult();
    }

    public List<R201SorguSonucu> getR201ListCriteriaForPaging(RaporSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        R201RaporCriteria criteria = new R201RaporCriteria(sorguKriteri,sessionInfo , getSessionFactory().getCurrentSession());
        return (List<R201SorguSonucu>) criteria.prepareResult();
    }

}
