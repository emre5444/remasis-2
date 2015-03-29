package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.DaireBorc;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.AidatSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by fcabi
 */
public class AidatCriteria {

    AidatSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<DaireBorc> prepareResult() {
        Criteria cr = session.createCriteria(DaireBorc.class, "db");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);
        cr.createAlias("db.borc", "b", JoinType.INNER_JOIN);
        cr.createAlias("db.daire", "d", JoinType.INNER_JOIN);
        cr.createAlias("b.islemTipi", "ist", JoinType.INNER_JOIN);
        cr.createAlias("d.blok", "bl", JoinType.INNER_JOIN);
        cr.createAlias("d.kullaniciDaireList", "kdl", JoinType.INNER_JOIN);
        cr.createAlias("kdl.kullanici", "k", JoinType.INNER_JOIN);

        cr.add(Restrictions.eq("b.durum.id", Durum.getAktifObject().getId()));
        cr.add(Restrictions.eq("b.sirket.id", sessionInfo.getSirket().getId()));

        if (sorguKriteri.getBlok() != null) {
            cr.add(Restrictions.eq("bl.aciklama", sorguKriteri.getBlok().getAciklama()));
        }

        if (sorguKriteri.getKullanici() != null) {
            cr.add(Restrictions.eq("k.id", sorguKriteri.getKullanici().getId()));
        }

        if (sorguKriteri.getDaireNo() != null) {
            cr.add(Restrictions.eq("d.daireNo", sorguKriteri.getDaireNo()));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getDekontNo())) {
            cr.add(Restrictions.eq("b.dekontNo", sorguKriteri.getDekontNo()));
        }

        if (sorguKriteri.getKaynakTipi() != null) {
            cr.add(Restrictions.eq("ist.aciklama", sorguKriteri.getKaynakTipi().getAciklama()));
        }

        if (sorguKriteri.getSorguBaslangicTarihi() != null) {
            cr.add(Restrictions.ge("b.islemTarihi", sorguKriteri.getSorguBaslangicTarihi()));
        }

        if (sorguKriteri.getSorguBitisTarihi() != null) {
            cr.add(Restrictions.le("b.islemTarihi", sorguKriteri.getSorguBitisTarihi()));
        }

        cr.addOrder(Order.desc("b.islemTarihi"));
        cr.addOrder(Order.asc("bl.id"));
        cr.addOrder(Order.asc("d.id"));
        return cr.list();
    }


    public AidatCriteria(AidatSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
