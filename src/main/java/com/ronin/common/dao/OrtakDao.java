/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.common.dao;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Il;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.KullaniciRol;
import com.ronin.common.model.Rol;
import com.ronin.model.*;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.*;
import com.ronin.model.criteria.AnketCriteria;
import com.ronin.model.criteria.DuyuruCriteria;
import com.ronin.model.criteria.IlanCriteria;
import com.ronin.model.criteria.RolCriteria;
import com.ronin.model.kriter.AnketSorguKriteri;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import com.ronin.model.kriter.IlanSorguKriteri;
import com.ronin.model.kriter.RolSorguKriteri;
import com.ronin.model.sorguSonucu.AnketSonucViewBean;
import com.ronin.utils.DateUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author msevim
 */
@Repository
public class OrtakDao implements IOrtakDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public IAbstractEntity getEntityByClass(Class cls, Long entityId) {
        return (IAbstractEntity) sessionFactory.getCurrentSession().get(cls, entityId);
    }

    @Override
    public IAbstractEntity getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IAbstractEntity> getListByNamedQuery(String namedQuery, Object... parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IAbstractEntity> getListByNamedQuery(String namedQuery) {
        return sessionFactory.getCurrentSession().getNamedQuery(namedQuery).list();
    }

    @Override
    public List<IAbstractEntity> getListByNamedQueryWithSirket(String namedQuery, SessionInfo sessionInfo) {
        return sessionFactory.getCurrentSession().getNamedQuery(namedQuery).setLong("sirketId", sessionInfo.getSirket().getId()).list();
    }

    public List<IAbstractEntity> getIlceListByNamedQueryWithIl(String namedQuery, Il il) {
        return sessionFactory.getCurrentSession().getNamedQuery(namedQuery).setLong("ilId", il.getId()).list();
    }

    public List<Duyuru> getDuyuruListBySorguSonucu(int first, int pageSize, DuyuruSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        StringBuffer sb = null;
        DuyuruCriteria criteria = new DuyuruCriteria(sorguKriteri, sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);
        return (List<Duyuru>) criteria.prepareResult();
    }

    public List<Duyuru> getIlanListBySorguSonucu(int first, int pageSize, IlanSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        StringBuffer sb = null;
        IlanCriteria criteria = new IlanCriteria(sorguKriteri, sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);
        return (List<Duyuru>) criteria.prepareResult();
    }

    public List<Anket> getAnketListBySorguSonucu(int first, int pageSize, AnketSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        StringBuffer sb = null;
        AnketCriteria criteria = new AnketCriteria(sorguKriteri, sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);
        return (List<Anket>) criteria.prepareResult();
    }

    public List<Duyuru> getAllDuyuruList(SessionInfo sessionInfo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Duyuru d where d.durum.id = ? " +
                        "and d.ilanMi.id = ? " +
                        "and d.sirket.id = ? " +
                        "order by d.id desc")
                .setParameter(0, Durum.ENUM.Aktif.getId())
                .setParameter(1, EvetHayir.ENUM.HAYIR_YOK.getId())
                .setParameter(2, sessionInfo.getSirket().getId())
                .list();
        return (List<Duyuru>) list;
    }

    public List<Notification> getAllNotificationList(SessionInfo sessionInfo, int limit) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery("from Notification n where n.kullanici.id = ?" +
                        "and n.sirket.id = ? " +
                        "and n.durum.id <> ? " +
                        "order by n.durum.id asc,n.id desc")
                .setParameter(0, sessionInfo.getKullanici().getId())
                .setParameter(1, sessionInfo.getSirket().getId())
                .setParameter(2, Durum.getSilinmisObject().getId());
        query.setMaxResults(limit);
        List list = query.list();
        return (List<Notification>) list;
    }

    public List<Duyuru> getAllIlanList(SessionInfo sessionInfo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Duyuru d where d.durum.id = ? " +
                        "and d.ilanMi.id = ? " +
                        "and d.sirket.id = ? " +
                        "order by d.id desc")
                .setParameter(0, Durum.ENUM.Aktif.getId())
                .setParameter(1, EvetHayir.ENUM.EVET_VAR.getId())
                .setParameter(2, sessionInfo.getSirket().getId())
                .list();
        return (List<Duyuru>) list;
    }

    public List<Duyuru> getAllDuyuruListByDaire(Daire daire) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Duyuru d where d.durum.id = ? and d.daire = ?")
                .setParameter(0, Durum.ENUM.Aktif.getId())
                .setParameter(1, daire).list();
        return (List<Duyuru>) list;

    }

    public List<Kullanici> getKullaniciByName(String name, SessionInfo sessionInfo) {
        List<Kullanici> kullaniciList = new ArrayList<Kullanici>();
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select ks from KullaniciSirket ks " +
                        "join fetch ks.kullanici k " +
                        "where ks.durum.id = ? " +
                        "and k.durum.id = ? " +
                        "and ks.sirket.id = ? " +
                        " and k.ad LIKE ? ")
                .setParameter(0, Durum.ENUM.Aktif.getId())
                .setParameter(1, Durum.ENUM.Aktif.getId())
                .setParameter(2, sessionInfo.getSirket().getId())
                .setParameter(3, "%" + name + "%")
                .list();

        for (KullaniciSirket ks : (List<KullaniciSirket>) list) {
            kullaniciList.add(ks.getKullanici());
        }

        return kullaniciList;
    }

    public void yeniAnketEkle(Anket anket, List<AnketSecim> anketSecimList) {
        // anket kaydedilir
        getSessionFactory().getCurrentSession().save(anket);
        for (AnketSecim anketSecim : anketSecimList)
            anketSecim.setAnket(anket);
        // anket seçim kaydedlir
        updateAiatSecim(anketSecimList, anket);
    }

    public void anketGuncelle(Anket anket, List<AnketSecim> anketSecimList) {
        // anket kaydedilir
        getSessionFactory().getCurrentSession().update(anket);
        for (AnketSecim anketSecim : anketSecimList)
            anketSecim.setAnket(anket);
        // anket seçim kaydedlir
        updateAiatSecim(anketSecimList, anket);
    }

    public void updateAiatSecim(List<AnketSecim> anketSecimList, Anket anket) {
        List<AnketSecim> currentAnketSecimList = getAnketSecimListFromAnket(anket);

        if (anketSecimList != null && anketSecimList.size() > 0) {
            for (AnketSecim anketSecim : anketSecimList) {
                if (!currentAnketSecimList.contains(anketSecim))
                    getSessionFactory().getCurrentSession().save(anketSecim);
            }
        }

        if (currentAnketSecimList != null && currentAnketSecimList.size() > 0) {
            for (AnketSecim anketSecim : currentAnketSecimList) {
                if (!anketSecimList.contains(anketSecim))
                    getSessionFactory().getCurrentSession().delete(anketSecim);
            }
        }
    }

    public List<AnketSecim> getAnketSecimListFromAnket(Anket anket) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select a from AnketSecim a " +
                        "where a.anket.id = ? ")
                .setParameter(0, anket.getId())
                .list();
        return (List<AnketSecim>) list;
    }

    public List<AnketKullanici> getKullaniciAnketListByAnket(Anket anket) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select a from AnketKullanici a " +
                        "where a.anket.id = ? ")
                .setParameter(0, anket.getId())
                .list();
        return (List<AnketKullanici>) list;
    }

    public void anketeKatilimEkleme(SessionInfo sessionInfo, Anket anket, AnketSecim anketSecim, String aciklama) {
        AnketKullanici anketKullanici = new AnketKullanici();
        anketKullanici.setAnket(anket);
        anketKullanici.setAnketSecim(anketSecim);
        anketKullanici.setKullanici(sessionInfo.getKullanici());
        anketKullanici.setTanitimZamani(DateUtils.getToday());
        anketKullanici.setAciklama(aciklama);
        getSessionFactory().getCurrentSession().save(anketKullanici);
    }

    public Integer getOysayisiByAnketSecim(AnketSecim anketSecim) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select a from AnketKullanici a " +
                        "where a.anketSecim.id = ? ")
                .setParameter(0, anketSecim.getId())
                .list();
        return list.size();
    }

    public List<AnketSonucViewBean> getAnketSonucDurum(SessionInfo sessionInfo, Anket anket) {
        List<AnketSonucViewBean> anketSonucViewBeanList = new ArrayList<>();
        List<AnketSecim> anketSecimList = getAnketSecimListFromAnket(anket);
        Integer toplam = 0;
        for (AnketSecim anketSecim : anketSecimList) {
            Integer oySayisi = getOysayisiByAnketSecim(anketSecim);
            AnketSonucViewBean anketSonucViewBean = new AnketSonucViewBean(oySayisi, null, anketSecim.getSecim());
            anketSonucViewBeanList.add(anketSonucViewBean);
            toplam = toplam + oySayisi;
        }

        for (AnketSonucViewBean anketSonucViewBean : anketSonucViewBeanList) {
            anketSonucViewBean.setRate((new Double(anketSonucViewBean.getTutar()) / new Double(toplam)) * 100);
        }
        return anketSonucViewBeanList;
    }

    public EvetHayir isAnketeKatinildiMi(SessionInfo sessionInfo, Anket anket) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select a from AnketKullanici a " +
                        "where a.anket.id = ? " +
                        "and a.kullanici.id = ? ")
                .setParameter(0, anket.getId())
                .setParameter(1, sessionInfo.getKullanici().getId())
                .list();
        return list.size() > 0 ? EvetHayir.getEvetObject() : EvetHayir.getHayirObject();
    }

    public Durum getSingleDurumEntity(Long id) {
        return (Durum) sessionFactory.getCurrentSession().get(Durum.class, id);
    }

    public KullaniciSirket getSingleKullaniciSirketEntity(Long id) {
        return (KullaniciSirket) sessionFactory.getCurrentSession().get(KullaniciSirket.class, id);
    }

    public Sirket getSingleSirketEntity(Long id) {
        return (Sirket) sessionFactory.getCurrentSession().get(Sirket.class, id);
    }

    public Rol getSingleRolEntity(Long id) {
        return (Rol) sessionFactory.getCurrentSession().get(Rol.class, id);
    }

    public AnketSecim getSingleAnketSecimEntity(Long id) {
        return (AnketSecim) sessionFactory.getCurrentSession().get(AnketSecim.class, id);
    }

    public Kategori getSingleKategoriEntity(Long id) {
        return (Kategori) sessionFactory.getCurrentSession().get(Kategori.class, id);
    }

    public Integer numberOfSirket() {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select count(S.id) from Sirket S").list();
        return ((Long) list.get(0)).intValue();
    }

    public void add(Duyuru duyuru) {
        getSessionFactory().getCurrentSession().save(duyuru);
    }


    public void update(Duyuru duyuru) {
        getSessionFactory().getCurrentSession().update(duyuru);
    }

    public void deleteBelge(Belge belge) {
        getSessionFactory().getCurrentSession().delete(belge);
    }

    public void mailGonder(Object mailSender) {
        getSessionFactory().getCurrentSession().save(mailSender);
    }

    public void createErisimLog(SessionInfo sessionInfo, Kullanici kullanici, LogTipi logTipi, String aciklama) {
        ErisimLog erisimLog = new ErisimLog();
        erisimLog.setAciklama(aciklama);
        erisimLog.setKullanici(kullanici);
        erisimLog.setLogTipi(logTipi);
        erisimLog.setTanitimZamani(new Date());
        erisimLog.setSirket(sessionInfo != null ? sessionInfo.getSirket() : null);
        getSessionFactory().getCurrentSession().save(erisimLog);
    }

    public Integer numberOfVisitors(SessionInfo sessionInfo, Date baslangicTarihi) {
        if (baslangicTarihi == null) {
            List list = getSessionFactory().getCurrentSession()
                    .createQuery("select distinct(E.kullanici.id) from ErisimLog E where E.sirket.id = ? and E.logTipi = ?")
                    .setParameter(0, sessionInfo.getSirket().getId())
                    .setParameter(1, LogTipi.getLoginObject())
                    .list();
            return list.size();
        } else {
            List list = getSessionFactory().getCurrentSession()
                    .createQuery("select distinct(E.kullanici.id) from ErisimLog E where E.sirket.id = ? and E.logTipi = ? and E.tanitimZamani >= ?")
                    .setParameter(0, sessionInfo.getSirket().getId())
                    .setParameter(1, LogTipi.getLoginObject())
                    .setParameter(2, baslangicTarihi)
                    .list();
            return list.size();
        }
    }

    public void deleteDuyuru(Duyuru duyuru) {
        duyuru.setDurum(Durum.getSilinmisObject());
        this.update(duyuru);
    }

    public void deleteAnket(Anket anket) {
        anket.setDurum(Durum.getSilinmisObject());
        getSessionFactory().getCurrentSession().update(anket);
    }

    public void update(Object object) {
        getSessionFactory().getCurrentSession().update(object);
    }

    public List<IletisimBilgileri> getAllIletisimBilgileriBySirket(SessionInfo sessionInfo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select ib from IletisimBilgileri ib " +
                        "where ib.sirket.id = ? ")
                .setParameter(0, sessionInfo.getSirket().getId())
                .list();
        return (List<IletisimBilgileri>) list;
    }

    public void updateIletisimBilgileri(IletisimBilgileri iletisimBilgileri) {
        getSessionFactory().getCurrentSession().update(iletisimBilgileri);
    }

    public void deleteIletisimBilgisi(IletisimBilgileri iletisimBilgileri) {
        getSessionFactory().getCurrentSession().delete(iletisimBilgileri);
    }

    public void iletisimBilgisiEkle(SessionInfo sessionInfo, IletisimBilgileri iletisimBilgileri) {
        getSessionFactory().getCurrentSession().save(iletisimBilgileri);
    }

    public void createHataLog(SessionInfo sessionInfo, String message, String stachTrace) {
        SistemHata sistemHata = new SistemHata();
        sistemHata.initSistemHata(sessionInfo, message, stachTrace);
        getSessionFactory().getCurrentSession().save(sistemHata);
    }

    public AnketKullanici getAnketKullanici(Anket anket, Long kullaniciId) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select a from AnketKullanici a " +
                        "where a.anket.id = ? and a.kullanici.id = ?")
                .setParameter(0, anket.getId())
                .setParameter(1, kullaniciId)
                .list();
        if (list.isEmpty()) {
            return null;
        }
        return (AnketKullanici) list.get(0);
    }
}
