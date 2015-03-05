package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.DaireBelge;
import com.ronin.model.Duyuru;
import com.ronin.model.constant.Belge;
import com.ronin.model.kriter.BelgeSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by ealtun on 13.03.2014.
 */
public class BelgeCriteria {

    BelgeSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Belge> prepareResult() {
        boolean isKey = false;
        Criteria cr = session.createCriteria(Belge.class, "b");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.createAlias("b.kullanici", "k", JoinType.INNER_JOIN);

        cr.add(Restrictions.eq("b.sirket.id", sessionInfo.getSirket().getId()));

        DetachedCriteria dc = DetachedCriteria.forClass(DaireBelge.class, "db");
        dc.createAlias("db.belge", "b1");
        dc.add(Restrictions.eqProperty("b.id", "b1.id"));
        dc.setProjection(Projections.id());
        cr.add(Subqueries.notExists(dc));

        if (!StringUtils.isEmpty(sorguKriteri.getBelgeAdi())) {
            cr.add(Restrictions.like("b.dataName", "%" + sorguKriteri.getBelgeAdi() + "%"));
        }

        if (sorguKriteri.getSorguBaslangicTarihi() != null) {
            cr.add(Restrictions.ge("b.islemTarihi", sorguKriteri.getSorguBaslangicTarihi()));
        }

        if (sorguKriteri.getSorguBitisTarihi() != null) {
            cr.add(Restrictions.le("b.islemTarihi", sorguKriteri.getSorguBitisTarihi()));
        }

        if (sorguKriteri.getKullanici() != null) {
            cr.add(Restrictions.eq("k.id", sorguKriteri.getKullanici().getId()));
        }

        if (sorguKriteri.getBelgeTipi() != null) {
            cr.add(Restrictions.eq("b.belgeTipi", sorguKriteri.getBelgeTipi()));
        }

        cr.addOrder(Order.desc("b.islemTarihi"));
        return cr.list();

    }


    public BelgeCriteria(BelgeSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
