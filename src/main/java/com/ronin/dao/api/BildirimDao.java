/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.model.BildirimTipiRol;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.BildirimTipiCriteria;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.model.kriter.HedefKitle;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author msevim
 */
@Repository
public class BildirimDao implements IBildirimDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public BildirimTipi getSingle(BildirimTipi bildirimTipi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BildirimTipi getSingleById(Long id) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from BildirimTipi where id=?")
                .setParameter(0, id).list();
        return (BildirimTipi) list.get(0);
    }

    public List<BildirimTipi> getAllBildirimTipiList() {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from BildirimTipi")
                .list();
        return (List<BildirimTipi>) list;
    }

    public List<BildirimTipi> getListCriteriaForPaging(int first, int pageSize, BildirimTipiSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        StringBuffer sb = null;

        BildirimTipiCriteria criteria = new BildirimTipiCriteria(sorguKriteri, sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);

        return (List<BildirimTipi>) criteria.prepareResult();
    }

    @Override
    public List<BildirimTipi> getList(BildirimTipi bildirimTipi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<Rol> getRolListByBildirimTipi(BildirimTipi bildirimTipi, SessionInfo sessionInfo) {

        List<Rol> rolList = new ArrayList<>();
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from BildirimTipiRol br join fetch br.rol r where br.bildirimTipi=? and r.sirket.id=?")
                .setParameter(0, bildirimTipi)
                .setParameter(1, sessionInfo.getSirket().getId()).list();
        for (BildirimTipiRol bildirimTipiRol : (List<BildirimTipiRol>) list) {
            rolList.add(bildirimTipiRol.getRol());
        }

        return rolList;

    }

    public List<Kullanici> getKullaniciListForBildirim(HedefKitle hedefKitle, SessionInfo sessionInfo) {

        List<Kullanici> kullaniciList = new ArrayList<>();
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from KullaniciDaire kd where kd.daire.blok = ? and kd.durum = ? and kd.kullanici.durum = ?")
                .setParameter(0, hedefKitle.getBlok())
                .setParameter(1, Durum.getAktifObject())
                .setParameter(2, Durum.getAktifObject()).list();
        for (KullaniciDaire kullaniciDaire : (List<KullaniciDaire>) list) {
            kullaniciList.add(kullaniciDaire.getKullanici());
        }

        return kullaniciList;

    }

    public void updateBldirimTipiRol(List<Rol> rolList, BildirimTipi bildirimTipi, SessionInfo sessionInfo) {
        //kullanicinin eski rolleri silinir
        Query query = getSessionFactory().getCurrentSession().createQuery("delete BildirimTipiRol br where br.bildirimTipi.id = :id and br.sirket = :sirket");
        query.setParameter("id", bildirimTipi.getId());
        query.setParameter("sirket", sessionInfo.getSirket());
        int result = query.executeUpdate();

        //yeni yetkiler eklenir.
        for (Rol rol : rolList) {
            addBildirimTipiRol(rol, bildirimTipi, sessionInfo);
        }
    }

    public void addBildirimTipiRol(Rol rol, BildirimTipi bildirimTipi, SessionInfo sessionInfo) {
        BildirimTipiRol bildirimTipiRol = new BildirimTipiRol();
        bildirimTipiRol.setRol(rol);
        bildirimTipiRol.setBildirimTipi(bildirimTipi);
        bildirimTipiRol.setSirket(sessionInfo.getSirket());
        getSessionFactory().getCurrentSession().save(bildirimTipiRol);
    }


}
