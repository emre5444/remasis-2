/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.common.dao;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.*;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.RolCriteria;
import com.ronin.model.kriter.RolSorguKriteri;
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
public class RolDao implements IRolDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Rol getSingle(Rol rol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rol getSingleById(Long id) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Rol where id=?")
                .setParameter(0, id).list();
        return (Rol) list.get(0);
    }

    public Yetki getYetkiById(Yetki yetki) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Yetki where id=?")
                .setParameter(0, yetki.getId()).list();
        return (Yetki) list.get(0);
    }

    public List<Rol> getListCriteriaForPaging(int first, int pageSize, RolSorguKriteri sorguKriteri , SessionInfo sessionInfo) {

        StringBuffer sb = null;

        RolCriteria criteria = new RolCriteria(sorguKriteri,sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);

        return (List<Rol>) criteria.prepareResult();
    }

    @Override
    public Rol getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rol> getList(Rol rol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rol> getListByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Yetki> getAllYetkiList() {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Yetki")
                .list();
        return (List<Yetki>) list;
    }

    public List<Rol> getAllRolList(SessionInfo sessionInfo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Rol r " +
                             "where r.sirket.id = ?")
                .setParameter(0, sessionInfo.getSirket().getId())
                .list();
        return (List<Rol>) list;
    }

    public void updateRolYetki(List<Yetki> yetkiList, Rol rol) {
        //rolun tum yetkileri silinir.
        Query query = getSessionFactory().getCurrentSession().createQuery("delete RolYetki ry where ry.rol.id = :id");
        query.setParameter("id", rol.getId());
        int result = query.executeUpdate();

        //yeni yetkiler eklenir.
        for (Yetki yetki : yetkiList) {
            addRolYetki(yetki, rol);
        }
    }

    public List<Rol> getRolListByKullanici(Kullanici kullanici , SessionInfo sessionInfo) {

        List<Rol> rolList = new ArrayList<>();
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select kr " +
                        "from KullaniciRol kr " +
                        "join fetch kr.rol r " +
                        "where kr.kullanici = ? " +
                        "and r.sirket.id = ?")
                .setParameter(0, kullanici)
                .setParameter(1, sessionInfo.getSirket().getId())
                .list();

        for (KullaniciRol kullaniciRol : (List<KullaniciRol>) list) {
            rolList.add(kullaniciRol.getRol());
        }

        return rolList;

    }

    public void updateKullaniciRol(List<Rol> rolList, Kullanici kullanici) {
        //kullanicinin eski rolleri silinir
        Query query = getSessionFactory().getCurrentSession().createQuery("delete KullaniciRol kr where kr.kullanici.id = :id");
        query.setParameter("id", kullanici.getId());
        int result = query.executeUpdate();

        //yeni yetkiler eklenir.
        for (Rol rol : rolList) {
            addKullaniciRol(rol, kullanici);
        }
    }

    public void addRolYetki(Yetki yetki, Rol rol) {
        RolYetki rolYetki = new RolYetki();
        rolYetki.setRol(rol);
        rolYetki.setYetki(yetki);
        getSessionFactory().getCurrentSession().save(rolYetki);
    }

    public void addKullaniciRol(Rol rol, Kullanici kullanici) {
        KullaniciRol kullaniciRol = new KullaniciRol();
        kullaniciRol.setRol(rol);
        kullaniciRol.setKullanici(kullanici);
        getSessionFactory().getCurrentSession().save(kullaniciRol);
    }

    @Override
    public int getCount(Rol rol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rol add(Rol rol) {
          Long rolId = (Long) getSessionFactory().getCurrentSession().save(rol);
        return  (Rol) sessionFactory.getCurrentSession().get(Rol.class, rolId);
    }

    @Override
    public void update(Rol rol) {
        getSessionFactory().getCurrentSession().update(rol);
    }

    @Override
    public void delete(Rol rol) {
        rol.setDurum(Durum.getSilinmisObject());
        this.update(rol);
    }

    @Override
    public void relateUserWithRole(Kullanici kullanici) {
        Rol rol = getSingleById(Rol.ENUM.User.getId());
        KullaniciRol kullaniciRol = new KullaniciRol();
        kullaniciRol.setRol(rol);
        kullaniciRol.setKullanici(kullanici);
        getSessionFactory().getCurrentSession().save(kullaniciRol);
    }

}
