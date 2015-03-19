/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.BlokCriteria;
import com.ronin.model.kriter.BlokSorguKriteri;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fcabi
 */
@Repository
public class BlokDao implements IBlokDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<Blok> getListCriteriaForPaging(int first, int pageSize, BlokSorguKriteri sorguKriteri, SessionInfo sessionInfo) {

        StringBuffer sb = null;

        BlokCriteria criteria = new BlokCriteria(sorguKriteri, sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);

        return (List<Blok>) criteria.prepareResult();
    }

    public Blok add(SessionInfo sessionInfo, Blok blok) {
        blok.setSirket(sessionInfo.getSirket());
        Long blokId = (Long) getSessionFactory().getCurrentSession().save(blok);
        return (Blok) sessionFactory.getCurrentSession().get(Blok.class, blokId);
    }


    public void update(SessionInfo sessionInfo, Blok blok) {
        blok.setSirket(sessionInfo.getSirket());
        getSessionFactory().getCurrentSession().update(blok);
    }


    public void delete(SessionInfo sessionInfo, Blok blok) {
        blok.setSirket(sessionInfo.getSirket());
        blok.setDurum(Durum.getPasifObject());
        getSessionFactory().getCurrentSession().update(blok);
    }

    public void addDaireListToBlok(SessionInfo sessionInfo, List<Daire> daireList, Blok blok){
        for(Daire daire : daireList){
            daire.setBlok(blok);
            getSessionFactory().getCurrentSession().save(blok);
        }
    }

    public List<Daire> getDaireListByBlok(Blok blok){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select d from Daire d where d.blok = ?")
                .setParameter(0, blok).list();
        return (List<Daire>) list;
    }

}
