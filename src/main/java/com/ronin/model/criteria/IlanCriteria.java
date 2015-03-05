package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Duyuru;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.DuyuruSorguKriteri;
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
public class IlanCriteria {

    IlanSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Duyuru> prepareResult() {
        boolean isKey = false;
        Criteria cr = session.createCriteria(Duyuru.class, "d");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.createAlias("d.daire", "dd", JoinType.INNER_JOIN);
        cr.createAlias("dd.blok", "b", JoinType.INNER_JOIN);
        cr.createAlias("d.kullanici", "k", JoinType.INNER_JOIN);


        cr.add(Restrictions.eq("d.durum.id", Durum.getAktifObject().getId()));
        cr.add(Restrictions.eq("d.sirket.id", sessionInfo.getSirket().getId()));
        cr.add(Restrictions.eq("d.ilanMi.id", EvetHayir.getEvetObject().getId()));


        if (!StringUtils.isEmpty(sorguKriteri.getKonu())) {
            cr.add(Restrictions.like("d.kisaAciklama", "%" + sorguKriteri.getKonu() + "%"));
        }

        if (sorguKriteri.getSorguBaslangicTarihi() != null) {
            cr.add(Restrictions.ge("d.tanitimZamani", sorguKriteri.getSorguBaslangicTarihi()));
        }

        if (sorguKriteri.getSorguBitisTarihi() != null) {
            cr.add(Restrictions.le("d.tanitimZamani", sorguKriteri.getSorguBitisTarihi()));
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

        cr.addOrder(Order.desc("d.tanitimZamani"));
        return cr.list();

    }


    public IlanCriteria(IlanSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
