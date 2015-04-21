package com.ronin.common.dao;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.model.Daire;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.SifreHatirlatma;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.constant.KullaniciTipi;
import com.ronin.model.criteria.KullaniciCriteria;
import com.ronin.model.kriter.KullaniciSorguKriteri;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class KullaniciDAO implements IKullaniciDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addKullanici(Kullanici user) {
        getSessionFactory().getCurrentSession().save(user);
    }

    public void deleteKullanici(Kullanici user) {
        getSessionFactory().getCurrentSession().delete(user);
    }

    public void updateKullanici(Kullanici user) {
        getSessionFactory().getCurrentSession().update(user);
    }

    public Kullanici getKullaniciById(Long id) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Kullanici where id=?")
                .setParameter(0, id).list();
        return (Kullanici) list.get(0);
    }
    
    public Kullanici getKullaniciByUsername(String username) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Kullanici where username=? and durum=?")
                .setParameter(0, username)
                .setParameter(1 , Durum.getAktifObject())
                .list();
        if(list.size()>0){
            return (Kullanici) list.get(0);
        }
        return null;
    }

    public Kullanici getKullaniciByEmail(String email) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Kullanici where email=? and durum=?")
                .setParameter(0, email)
                .setParameter(1 , Durum.getAktifObject())
                .list();
        if(list.size()>0){
            return (Kullanici) list.get(0);
        }
        return null;
    }

    public long getBekleyenSifreIslemi(Long kullaniciId) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select count(SH.id) from SifreHatirlatma SH where SH.kullanici.id=? ")
                .setParameter(0, kullaniciId)
                .list();
        return (Long) list.get(0);
    }

    public List<Kullanici> getKullaniciList() {
        List list = getSessionFactory().getCurrentSession().createQuery("from Kullanici").setCacheable(true).list();
        return list;
    }

    public List<Kullanici> getListCriteriaForPaging(int first, int pageSize, KullaniciSorguKriteri sorguKriteri , SessionInfo sessionInfo) {

        StringBuffer sb = null;

        KullaniciCriteria criteria = new KullaniciCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession(), first, pageSize);


        return (List<Kullanici>) criteria.prepareResult();
    }

    public void updateKullaniciDaire(List<KullaniciDaire> kullaniciDaireList, Kullanici kullanici) {
        //kullanicinin eski daireleri silinir
        Query query = getSessionFactory().getCurrentSession().createQuery("delete KullaniciDaire kd where kd.kullanici.id = :id");
        query.setParameter("id", kullanici.getId());
        int result = query.executeUpdate();

        //yeni yetkiler eklenir.
        for (KullaniciDaire kullaniciDaire : kullaniciDaireList) {
            List<KullaniciDaire> mevcutKullaniciDaireList = getKullaniciListByDaire(kullaniciDaire.getDaire());
            if (mevcutKullaniciDaireList.isEmpty()) {
                kullaniciDaire.setVarsayilanMi(EvetHayir.getEvetObject());
            } else {
                for (KullaniciDaire mevcutKullaniciDaire : mevcutKullaniciDaireList) {
                    if (mevcutKullaniciDaire.getKullaniciTipi().isEvsahibiMi()) {
                        mevcutKullaniciDaire.setVarsayilanMi(EvetHayir.getHayirObject());
                        kullaniciDaire.setVarsayilanMi(EvetHayir.getEvetObject());
                        getSessionFactory().getCurrentSession().save(mevcutKullaniciDaire);
                    } else{
                        mevcutKullaniciDaire.setVarsayilanMi(EvetHayir.getEvetObject());
                        kullaniciDaire.setVarsayilanMi(EvetHayir.getHayirObject());
                        getSessionFactory().getCurrentSession().save(mevcutKullaniciDaire);
                    }
                }
            }
            getSessionFactory().getCurrentSession().save(kullaniciDaire);
        }
    }

    public List<KullaniciDaire> getDaireListByKullanici(Kullanici kullanici){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from KullaniciDaire kd where kd.kullanici=?")
                .setParameter(0, kullanici).list();
        if(list.size()>0){
            return (List<KullaniciDaire>) list;
        }
        return null;
    }

    public List<KullaniciDaire> getKullaniciListByDaire(Daire daire){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from KullaniciDaire kd where kd.daire=?")
                .setParameter(0, daire).list();
        if(list.size()>0){
            return (List<KullaniciDaire>) list;
        }
        return null;
    }

    public List<KullaniciDaire> getVarsayilanKullaniciListByDaire(Daire daire){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from KullaniciDaire kd where kd.daire=? and " +
                                                            "kd.varsayilanMi.id = ?")
                .setParameter(0, daire)
                .setParameter(1, EvetHayir.getEvetObject().getId())
                .list();
        if(list.size()>0){
            return (List<KullaniciDaire>) list;
        }
        return null;
    }

    public List<KullaniciDaire> getKullaniciListByDaire(Daire daire , KullaniciTipi kullaniciTipi){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from KullaniciDaire kd where kd.daire=? and kd.kullaniciTipi.id=?")
                .setParameter(0, daire)
                .setParameter(1, kullaniciTipi.getId())
                .list();
        if(list.size()>0){
            return (List<KullaniciDaire>) list;
        }
        return null;
    }


    public void addKullaniciSirket(KullaniciSirket kullaniciSirket) {
        getSessionFactory().getCurrentSession().save(kullaniciSirket);
    }

    public void sifreHatirlatmaIstekGonder(SifreHatirlatma sifreHatirlatma) {
        getSessionFactory().getCurrentSession().save(sifreHatirlatma);
    }

}
