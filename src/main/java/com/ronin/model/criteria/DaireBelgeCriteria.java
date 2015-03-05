package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.DaireBelge;
import com.ronin.model.Duyuru;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.BelgeSorguKriteri;
import com.ronin.model.kriter.IlanSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by ealtun on 13.03.2014.
 */
public class DaireBelgeCriteria {

    BelgeSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<DaireBelge> prepareResult() {
        boolean isKey = false;
        Criteria cr = session.createCriteria(DaireBelge.class, "db");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.createAlias("db.daire", "dd", JoinType.INNER_JOIN);
        cr.createAlias("db.belge", "bb", JoinType.INNER_JOIN);
        cr.createAlias("dd.blok", "b", JoinType.INNER_JOIN);
        cr.createAlias("bb.kullanici", "k", JoinType.INNER_JOIN);

        cr.add(Restrictions.eq("bb.sirket.id", sessionInfo.getSirket().getId()));

        if (!StringUtils.isEmpty(sorguKriteri.getBelgeAdi())) {
            cr.add(Restrictions.like("bb.dataName", "%" + sorguKriteri.getBelgeAdi() + "%"));
        }

        if (sorguKriteri.getSorguBaslangicTarihi() != null) {
            cr.add(Restrictions.ge("bb.islemTarihi", sorguKriteri.getSorguBaslangicTarihi()));
        }

        if (sorguKriteri.getSorguBitisTarihi() != null) {
            cr.add(Restrictions.le("bb.islemTarihi", sorguKriteri.getSorguBitisTarihi()));
        }

        if (sorguKriteri.getBlok() != null) {
            cr.add(Restrictions.eq("b.aciklama", sorguKriteri.getBlok().getAciklama()));
        }

        if (sorguKriteri.getKullanici() != null) {
            cr.add(Restrictions.eq("k.id", sorguKriteri.getKullanici().getId()));
        }

        if (sorguKriteri.getDaireNo() != null) {
            cr.add(Restrictions.eq("dd.daireNo", sorguKriteri.getDaireNo()));
        }

        if (sorguKriteri.getBelgeTipi() != null) {
            cr.add(Restrictions.eq("bb.belgeTipi", sorguKriteri.getBelgeTipi()));
        }

        cr.addOrder(Order.desc("bb.islemTarihi"));
        return cr.list();

    }


    public DaireBelgeCriteria(BelgeSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
