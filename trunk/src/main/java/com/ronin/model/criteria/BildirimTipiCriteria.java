package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Rol;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.kriter.BildirimTipiSorguKriteri;
import com.ronin.model.kriter.RolSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by msevim on 19.03.2014.
 */
public class BildirimTipiCriteria {

    BildirimTipiSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<BildirimTipi> prepareResult() {

        Criteria cr = session.createCriteria(BildirimTipi.class, "bt");
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        if (sorguKriteri.getBildirimTipi() != null) {
            cr.add(Restrictions.eq("bt.id", sorguKriteri.getBildirimTipi().getId()));
        }

        return cr.list();
    }

    public BildirimTipiCriteria(BildirimTipiSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
