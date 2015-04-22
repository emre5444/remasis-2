/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.model.*;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.DaireCriteria;
import com.ronin.model.kriter.DaireSorguKriteri;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author esimsek
 */
@Repository
public class DaireDao implements IDaireDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public Daire getSingle(Daire arac) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Daire getSingleById(Long id) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Daire where id=?")
                .setParameter(0, id).list();
        return (Daire) list.get(0);
    }


    public Daire getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<Daire> getList(Daire arac) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<Daire> getListByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Daire> getListCriteriaForPaging(int first, int pageSize, DaireSorguKriteri sorguKriteri , SessionInfo sessionInfo) {

        StringBuffer sb = null;

        DaireCriteria criteria = new DaireCriteria(sorguKriteri,sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);


        return (List<Daire>) criteria.prepareResult();
    }


    public int getCountByCriteriaForPaging(DaireSorguKriteri sorguKriteri) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select count(M.id) from Daire M").list();
        return ((Long) list.get(0)).intValue();
    }

    public List<DaireBorc> getBorcListByDaire(Daire daire){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select db " +
                             "from DaireBorc db " +
                             "join fetch db.daire d " +
                             "join fetch db.borc b " +
                             "where b.durum.id = ? " +
                             "and d.id = ? " +
                             "order by b.islemTarihi desc , b.islemTipi desc , b.borc asc ")
                .setParameter(0 , Durum.getAktifObject().getId())
                .setParameter(1 , daire.getId())
                .list();
        return (List<DaireBorc>)list;
    }

    public Borc getBakiyeOfDaire(Daire daire){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select db " +
                        "from DaireBorc db " +
                        "join fetch db.daire d " +
                        "join fetch db.borc b " +
                        "where b.durum.id = ? " +
                        "and d.id = ? " +
                        "and b.islemTarihi = (select max(dab.borc.islemTarihi) from DaireBorc dab " +
                                      "where dab.daire.id = ?) ")
                .setParameter(0, daire.getId())
                .setParameter(1, Durum.getAktifObject().getId())
                .setParameter(2, daire.getId())
                .list();

        if(list.size() > 0){
            return ((DaireBorc)list.get(0)).getBorc();
        }
        return new Borc();
    }

    public int getCount(Daire arac) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void add( Daire arac) {
        getSessionFactory().getCurrentSession().save(arac);
    }


    public void update(Daire arac) {
        getSessionFactory().getCurrentSession().update(arac);
    }

    public void addDaireListToBlok(SessionInfo sessionInfo, List<Daire> daireList){
        for(Daire daire : daireList){
            getSessionFactory().getCurrentSession().save(daire);
        }
    }

    public void daireSakinEkleme(DaireSakin daireSakin , SessionInfo sessionInfo) {
        Kullanici kullanici = daireSakin.getKullanici();
        // kullanici kaydi olusturulur.
        getSessionFactory().getCurrentSession().save(kullanici);
        getSessionFactory().getCurrentSession().save(daireSakin);
    }

    public void daireAracEkleme(DaireArac daireArac , SessionInfo sessionInfo) {
        getSessionFactory().getCurrentSession().save(daireArac);
    }

    public void daireSakinGuncelleme(DaireSakin daireSakin , SessionInfo sessionInfo) {
        getSessionFactory().getCurrentSession().update(daireSakin.getKullanici());
    }

    public void daireAracGuncelleme(DaireArac daireArac , SessionInfo sessionInfo) {
        getSessionFactory().getCurrentSession().update(daireArac);
    }

    public void daireSakinSilme(DaireSakin daireSakin , SessionInfo sessionInfo) {
        daireSakin.setDurum(Durum.getSilinmisObject());
        getSessionFactory().getCurrentSession().update(daireSakin);
    }

    public void daireAracSilme(DaireArac daireArac , SessionInfo sessionInfo) {
        daireArac.setDurum(Durum.getSilinmisObject());
        getSessionFactory().getCurrentSession().update(daireArac);
    }

    public List<DaireSakin> getDaireSakinListByDaire(Daire daire , Kullanici kullanici , SessionInfo sessionInfo){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select ds from DaireSakin ds where ds.daire.id =? and ds.bagliKullanici.id =? and ds.durum.id=?")
                .setParameter(0, daire.getId())
                .setParameter(1, kullanici.getId())
                .setParameter(2, Durum.getAktifObject().getId())
                .list();
        return list;
    }

    public List<DaireArac> getDaireAracListByDaire(Daire daire , Kullanici kullanici , SessionInfo sessionInfo){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select da from DaireArac da where da.daire.id =? and da.kullanici.id =? and da.durum.id=?")
                .setParameter(0, daire.getId())
                .setParameter(1, kullanici.getId())
                .setParameter(2, Durum.getAktifObject().getId())
                .list();
        return list;
    }

    public void delete(Daire arac) {
        this.update(arac);
    }


}
