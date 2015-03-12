package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IKullaniciDAO;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.DaireBorcKalem;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.constant.BorcTipi;
import com.ronin.model.constant.Durum;
import com.ronin.model.criteria.AidatCriteria;
import com.ronin.model.kriter.AidatSorguKriteri;
import com.ronin.model.sorguSonucu.BorcAlacakViewBean;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fcabi on 12.07.2014.
 */
@Repository
public class FinansalIslemlerDao implements IFinansalIslemlerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    private IKullaniciDAO iKullaniciDAO;

    public List<BorcAlacakViewBean> getBorcAlacakDurumuForDaire(SessionInfo sessionInfo, Daire daire, Date dateWitoutTime) {
        List<BorcAlacakViewBean> borcAlacakList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        Double borc = 0.0;
        Double alacak = 0.0;

        String sql = "";
        List list = null;
        if (dateWitoutTime != null) {
            sql = "from DaireBorc db " +
                    "join fetch db.daire d " +
                    "join fetch db.borc bb " +
                    "join fetch d.blok b " +
                    "where d.id = ? " +
                    "and b.sirket.id = ? " +
                    "and bb.islemTarihi >= ? " +
                    "and bb.durum = ?";

            list = getSessionFactory().getCurrentSession()
                    .createQuery(sql)
                    .setParameter(0, daire.getId())
                    .setParameter(1, sessionInfo.getSirket().getId())
                    .setParameter(2, dateWitoutTime)
                    .setParameter(3, Durum.getAktifObject())
                    .list();
        } else {
            sql = "from DaireBorc db " +
                    "join fetch db.daire d " +
                    "join fetch db.borc bb " +
                    "join fetch d.blok b " +
                    "where d.id = ? " +
                    "and b.sirket.id = ? " +
                    "and bb.durum = ?";

            list = getSessionFactory().getCurrentSession()
                    .createQuery(sql)
                    .setParameter(0, daire.getId())
                    .setParameter(1, sessionInfo.getSirket().getId())
                    .setParameter(2, Durum.getAktifObject())
                    .list();
        }

        for (DaireBorc db : (List<DaireBorc>) list) {
            borc += db.getBorc().getBorc() != null ? db.getBorc().getBorc() : 0.0;
            alacak += db.getBorc().getOdenenTutar() != null ? db.getBorc().getOdenenTutar() : 0.0;
        }
        Double toplam = borc + alacak;
        Double borcRate = (borc / toplam) * 100;
        Double alacakRate = (alacak / toplam) * 100;
        borcAlacakList.add(new BorcAlacakViewBean(daire, new BorcTipi(BorcTipi.ENUM.BORC), borc, borcRate));
        borcAlacakList.add(new BorcAlacakViewBean(daire, new BorcTipi(BorcTipi.ENUM.ALACAK), alacak, alacakRate));
        return borcAlacakList;
    }

    public List<BorcAlacakViewBean> getBorcAlacakDurumu(SessionInfo sessionInfo, Date dateWitoutTime) {
        List<BorcAlacakViewBean> borcAlacakList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        if (sessionInfo.isAdminMi()) {
            String sql = "select IFNULL(sum(b.borc) , 0) as borc ," +
                    "IFNULL(sum(b.odenen_tutar), 0) as odenen_tutar " +
                    "from borc b " +
                    "where b.durum_id = 1 " +
                    "and b.sirket_id = :sirket_id";

            if (dateWitoutTime != null) {
                sql += " and b.islem_tarihi >= :firstDate";
            }

            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter("sirket_id", sessionInfo.getSirket().getId());
            if (dateWitoutTime != null) {
                query.setParameter("firstDate", dateWitoutTime);
            }
            List data = query.list();

            for (Object object : data) {
                Map row = (Map) object;
                Double borc = new Double(row.get("borc").toString());
                Double alacak = new Double(row.get("odenen_tutar").toString());
                Double toplam = borc + alacak;
                Double borcRate = (borc / toplam) * 100;
                Double alacakRate = (alacak / toplam) * 100;
                borcAlacakList.add(new BorcAlacakViewBean(new BorcTipi(BorcTipi.ENUM.BORC), borc, borcRate));
                borcAlacakList.add(new BorcAlacakViewBean(new BorcTipi(BorcTipi.ENUM.ALACAK), alacak, alacakRate));
            }

        } else {
            List<KullaniciDaire> kullaniciDaireList = getiKullaniciDAO().getDaireListByKullanici(sessionInfo.getKullanici());
            Double borc = 0.0;
            Double alacak = 0.0;
            for (KullaniciDaire kd : kullaniciDaireList) {
                String sql = "";
                List list = null;
                if (dateWitoutTime != null) {
                    sql = "from DaireBorc db " +
                            "join fetch db.daire d " +
                            "join fetch db.borc bb " +
                            "join fetch d.blok b " +
                            "where d.id = ? " +
                            "and b.sirket.id = ? " +
                            "and bb.islemTarihi >= ? " +
                            "and bb.durum = ?";

                    list = getSessionFactory().getCurrentSession()
                            .createQuery(sql)
                            .setParameter(0, kd.getDaire().getId())
                            .setParameter(1, sessionInfo.getSirket().getId())
                            .setParameter(2, dateWitoutTime)
                            .setParameter(3,Durum.getAktifObject())
                            .list();
                } else {
                    sql = "from DaireBorc db " +
                            "join fetch db.daire d " +
                            "join fetch d.blok b " +
                            "where d.id = ? " +
                            "and b.sirket.id = ? ";

                    list = getSessionFactory().getCurrentSession()
                            .createQuery(sql)
                            .setParameter(0, kd.getDaire().getId())
                            .setParameter(1, sessionInfo.getSirket().getId())
                            .list();
                }

                for (DaireBorc db : (List<DaireBorc>) list) {
                    borc += db.getBorc().getBorc();
                    alacak += db.getBorc().getOdenenTutar();
                }
                Double toplam = borc + alacak;
                Double borcRate = (borc / toplam) * 100;
                Double alacakRate = (alacak / toplam) * 100;
                borcAlacakList.add(new BorcAlacakViewBean(kd.getDaire(), new BorcTipi(BorcTipi.ENUM.BORC), borc, borcRate));
                borcAlacakList.add(new BorcAlacakViewBean(kd.getDaire(), new BorcTipi(BorcTipi.ENUM.ALACAK), alacak, alacakRate));
            }
        }
        return borcAlacakList;
    }

    public List<DaireBorc> getListCriteriaForPaging(int first, int pageSize, AidatSorguKriteri
            sorguKriteri, SessionInfo sessionInfo) {
        AidatCriteria criteria = new AidatCriteria(sorguKriteri, sessionInfo, getSessionFactory().getCurrentSession(), first, pageSize);
        return criteria.prepareResult();
    }

    public void addDaireBorc(DaireBorc daireBorc) {
        getSessionFactory().getCurrentSession().save(daireBorc);
    }

    public void addDaireBorcKalem(DaireBorcKalem daireBorcKalem) {
        getSessionFactory().getCurrentSession().save(daireBorcKalem);
    }


    public IKullaniciDAO getiKullaniciDAO() {
        return iKullaniciDAO;
    }

    public void setiKullaniciDAO(IKullaniciDAO iKullaniciDAO) {
        this.iKullaniciDAO = iKullaniciDAO;
    }

    public void updateObject(Object object) {
        getSessionFactory().getCurrentSession().update(object);
    }
}
