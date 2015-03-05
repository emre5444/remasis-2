/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.*;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.KaynakTipi;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.criteria.DaireCriteria;
import com.ronin.model.criteria.TalepCriteria;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.TalepSorguKriteri;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author esimsek
 */
@Repository
public class TalepDao implements ITalepDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public TalepDaire getSingleById(Long id) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Daire where id=?")
                .setParameter(0, id).list();
        return (TalepDaire) list.get(0);
    }

    public TalepTipi getSingleTalepTipiByEnum(TalepTipi.ENUM talepEnum){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from TalepTipi where id=?")
                .setParameter(0, talepEnum.getId()).list();
        return (TalepTipi) list.get(0);
    }


    public TalepTipi getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<TalepTipi> getListByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<TalepDaire> getListCriteriaForPaging(int first, int pageSize, TalepSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        StringBuffer sb = null;
        TalepCriteria criteria = new TalepCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession(), first, pageSize);
        return (List<TalepDaire>) criteria.prepareResult();
    }


    public List<TalepDaire> getTalepListByDaire(Daire daire){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select db " +
                             "from DaireBorc db " +
                             "join fetch db.daire d " +
                             "join fetch db.borc b " +
                             "where b.durum.id = ? " +
                             "and d.id = ? " +
                             "order by b.odemeVadesi desc ")
                .setParameter(0 , Durum.getAktifObject().getId())
                .setParameter(1 , daire.getId())
                .list();

        return (List<TalepDaire>)list;
    }


    public int getCountTalep(Daire daire) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void addTalep(SessionInfo sessionInfo ,Talep talep) {
        talep.setSirket(sessionInfo.getSirket());
        getSessionFactory().getCurrentSession().save(talep);
    }


    public void updateTalep(SessionInfo sessionInfo ,Talep talep) {
        talep.setSirket(sessionInfo.getSirket());
        getSessionFactory().getCurrentSession().update(talep);
    }


    public void deleteTalep(TalepDaire talepDaire) {
        talepDaire.setDurum(Durum.getSilinmisObject());
        this.updateTalep(talepDaire);
    }

    public void updateTalep(TalepDaire talepDaire) {
        getSessionFactory().getCurrentSession().update(talepDaire);
    }

    public void talepOnayla(TalepDaire talepDaire) {
        getSessionFactory().getCurrentSession().update(talepDaire);
    }

    public void talepReddet(TalepDaire talepDaire) {
        getSessionFactory().getCurrentSession().update(talepDaire);
    }

    public void arizaTalebiEkleme(ArizaTalebi talep , Daire daire){
        TalepDaire td = new TalepDaire();
        td.setTalep(talep);
        td.setDaire(daire);
        td.setDurum(Durum.getAktifObject());
        getSessionFactory().getCurrentSession().save(talep);
        getSessionFactory().getCurrentSession().save(td);
    }

    public void sikayetTalebiEkleme(SikayetTalebi talep , Daire daire){
        TalepDaire td = new TalepDaire();
        td.setTalep(talep);
        td.setDaire(daire);
        td.setDurum(Durum.getAktifObject());
        getSessionFactory().getCurrentSession().save(talep);
        getSessionFactory().getCurrentSession().save(td);
    }

    public void belgeTalebiEkleme(BelgeTalebi talep , Daire daire){
        TalepDaire td = new TalepDaire();
        td.setTalep(talep);
        td.setDaire(daire);
        td.setDurum(Durum.getAktifObject());
        getSessionFactory().getCurrentSession().save(talep);
        getSessionFactory().getCurrentSession().save(td);
    }

    public void itirazTalebiEkleme(ItirazTalebi talep , Daire daire){
        TalepDaire td = new TalepDaire();
        td.setTalep(talep);
        td.setDaire(daire);
        td.setDurum(Durum.getAktifObject());
        getSessionFactory().getCurrentSession().save(talep);
        getSessionFactory().getCurrentSession().save(td);
    }

    public DaireBorc getItirazEdilenDaireBorc(DaireBorc daireBorc){
           if(daireBorc.getBorc().getIslemTipi().isBorcDekontuMu()){
               daireBorc.getBorc().setOdemeVadesi(null);
               return daireBorc;
           } else {
               List list = getSessionFactory().getCurrentSession()
                       .createQuery("select db " +
                               "from DaireBorc db " +
                               "join fetch db.daire d " +
                               "join fetch db.borc b " +
                               "join fetch b.islemTipi it " +
                               "where b.durum.id = ? " +
                               "and d.id = ? " +
                               "and b.islemTarihi = ? " +
                               "and it.id = ? " +
                               "order by b.odemeVadesi desc ")
                       .setParameter(0, Durum.getAktifObject().getId())
                       .setParameter(1, daireBorc.getDaire().getId())
                       .setParameter(2 , daireBorc.getBorc().getIslemTarihi())
                       .setParameter(3, KaynakTipi.getOdemeObject().getId())
                       .list();
               Double toplamOdeme = 0.0;
               for(DaireBorc db : (List<DaireBorc>)list){
                   toplamOdeme+= db.getBorc().getOdenenTutar();
               }
               daireBorc.getBorc().setOdenenTutar(toplamOdeme);
               daireBorc.getBorc().setDekontNo(null);
               return daireBorc;
           }

    }
}
