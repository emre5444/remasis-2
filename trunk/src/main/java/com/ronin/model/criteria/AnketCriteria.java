package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Anket;
import com.ronin.model.Duyuru;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.AnketSorguKriteri;
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
public class AnketCriteria {

    AnketSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Anket> prepareResult() {
        boolean isKey = false;
        Criteria cr = session.createCriteria(Anket.class, "a");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.createAlias("a.kullanici", "k", JoinType.INNER_JOIN);
      //  cr.createAlias("a.anketKullaniciList", "akl", JoinType.LEFT_OUTER_JOIN);

        cr.add(Restrictions.eq("a.sirket.id", sessionInfo.getSirket().getId()));
        cr.add(Restrictions.eq("a.durum.id", Durum.getAktifObject().getId()));

        if (!StringUtils.isEmpty(sorguKriteri.getAnketKonusu())) {
            cr.add(Restrictions.like("a.anketKonusu", "%" + sorguKriteri.getAnketKonusu() + "%"));
        }

        if (sorguKriteri.getSorguBaslangicTarihi() != null) {
            cr.add(Restrictions.ge("a.tanitimZamani", sorguKriteri.getSorguBaslangicTarihi()));
        }

        if (sorguKriteri.getSorguBitisTarihi() != null) {
            cr.add(Restrictions.le("a.tanitimZamani", sorguKriteri.getSorguBitisTarihi()));
        }

        if (sorguKriteri.getAnketAktifMi() != null) {
            cr.add(Restrictions.eq("a.aktifMi.id", sorguKriteri.getAnketAktifMi().getId()));
        }

        cr.addOrder(Order.desc("a.tanitimZamani"));
        return cr.list();

    }


    public AnketCriteria(AnketSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
