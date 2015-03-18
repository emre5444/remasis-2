package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Rol;
import com.ronin.model.kriter.RolSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by msevim on 19.03.2014.
 */
public class RolCriteria {

    RolSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Rol> prepareResult() {

        Criteria cr = session.createCriteria(Rol.class, "r");
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);
        cr.add(Restrictions.eq("r.sirket.id", sessionInfo.getSirket().getId()));

        if (!StringUtils.isEmpty(sorguKriteri.getAd())) {
            cr.add(Restrictions.like("r.ad", "%" + sorguKriteri.getAd() + "%"));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getDurum())) {
            cr.add(Restrictions.like("r.durum", sorguKriteri.getDurum()));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getYetkiAdi())) {
            cr.createAlias("r.rolYetkiList", "ryl" , JoinType.INNER_JOIN);
            cr.createAlias("ryl.yetki", "y" , JoinType.INNER_JOIN);
            cr.add(Restrictions.like("y.ad", "%" + sorguKriteri.getYetkiAdi() + "%"));
        }

        return cr.list();
    }

    public RolCriteria(RolSorguKriteri sorguKriteri, SessionInfo sessionInfo ,Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
