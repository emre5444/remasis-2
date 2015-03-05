package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.constant.Blok;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.BlokSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by fcabi
 */
public class BlokCriteria {

    BlokSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Blok> prepareResult() {

        Criteria cr = session.createCriteria(Blok.class, "b");
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.add(Restrictions.eq("b.sirket.id", sessionInfo.getSirket().getId()));
        cr.add(Restrictions.eq("b.durum", Durum.getAktifObject()));

        if (!StringUtils.isEmpty(sorguKriteri.getAciklama())) {
            cr.add(Restrictions.eq("b.aciklama", sorguKriteri.getAciklama()));
        }

        return cr.list();
    }

    public BlokCriteria(BlokSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
