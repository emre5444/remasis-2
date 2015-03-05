/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Envanter;
import com.ronin.model.Kategori;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.EnvanterCriteria;
import com.ronin.model.criteria.KategoriCriteria;
import com.ronin.model.kriter.EnvanterSorguKriteri;
import com.ronin.model.kriter.KategoriSorguKriteri;
import com.ronin.utils.DateUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author msevim
 */
@Repository
public class EnvanterDao implements IEnvanterDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<Kategori> getKategoriListBySorguKriteri(int first, int pageSize, KategoriSorguKriteri sorguKriteri , SessionInfo sessionInfo){
        StringBuffer sb = null;
        KategoriCriteria criteria = new KategoriCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession(), first, pageSize);
        return (List<Kategori>) criteria.prepareResult();
    }

    public List<Envanter> getEnvanterListBySorguKriteri(int first, int pageSize, EnvanterSorguKriteri sorguKriteri , SessionInfo sessionInfo){
        StringBuffer sb = null;
        EnvanterCriteria criteria = new EnvanterCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession(), first, pageSize);
        return (List<Envanter>) criteria.prepareResult();
    }

    public void save(Object object){
        getSessionFactory().getCurrentSession().save(object);
    }

    public void envanterSilme(SessionInfo sessionInfo , Envanter kategori){
        kategori.setDurum(Durum.getSilinmisObject());
        getSessionFactory().getCurrentSession().update(kategori);
    }

    public void envanterGuncelleme(SessionInfo sessionInfo , Envanter kategori){
        getSessionFactory().getCurrentSession().update(kategori);
    }

    public void kategoriEkleme(SessionInfo sessionInfo , Kategori kategori){
        kategori.setSirket(sessionInfo.getSirket());
        kategori.setDurum(Durum.getAktifObject());
        kategori.setTanitimZamani(DateUtils.getNow());
        getSessionFactory().getCurrentSession().save(kategori);
    }

    public void kategoriSilme(SessionInfo sessionInfo , Kategori kategori){
        kategori.setDurum(Durum.getSilinmisObject());
        getSessionFactory().getCurrentSession().update(kategori);
    }

    public Envanter getEnvanterByBarkodNo(String barkodNo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Envanter where barkodNo=?")
                .setParameter(0, barkodNo)
                .list();
        if (list.size() > 0) {
            return (Envanter) list.get(0);
        }
        return null;
    }

}
