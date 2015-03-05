package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.kriter.DaireSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.List;

/**
 * Created by ealtun on 13.03.2014.
 */
public class DaireCriteria {

    DaireSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Daire> prepareResult() {

        Criteria cr = session.createCriteria(Daire.class, "d");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);
        cr.createAlias("d.blok", "b", JoinType.INNER_JOIN);
        cr.createAlias("d.daireTipi", "dt", JoinType.INNER_JOIN);
        cr.createAlias("d.kullaniciDaireList", "kdl", JoinType.LEFT_OUTER_JOIN);
        cr.createAlias("kdl.kullanici", "k", JoinType.LEFT_OUTER_JOIN);

        cr.add(Restrictions.eq("b.sirket.id", sessionInfo.getSirket().getId()));

        if (sorguKriteri.getDaireNo() != null) {
            cr.add(Restrictions.eq("d.daireNo", sorguKriteri.getDaireNo()));
        }

        if (sorguKriteri.getBlok() != null) {
            cr.add(Restrictions.eq("b.aciklama", sorguKriteri.getBlok().getAciklama()));
        }

        if (sorguKriteri.getBlok() != null) {
            cr.add(Restrictions.eq("b.id", sorguKriteri.getBlok().getId()));
        }

        if (sorguKriteri.getKat() != null) {
            cr.add(Restrictions.eq("d.kat", sorguKriteri.getKat()));
        }

        if (sorguKriteri.getKullanici() != null) {
            cr.add(Restrictions.eq("k.id", sorguKriteri.getKullanici().getId()));
        }

        if (sorguKriteri.getDaireTipi() != null) {
            cr.add(Restrictions.eq("dt.aciklama", sorguKriteri.getDaireTipi().getAciklama()));
        }

        if (sorguKriteri.getSelectedDaireList() != null && !sorguKriteri.getSelectedDaireList().isEmpty()) {
            cr.add(Restrictions.not(Restrictions.in("d.id", sorguKriteri.getSelectedDaireList())));
        }

        return cr.list();

    }


    public DaireCriteria(DaireSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
