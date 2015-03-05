package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Kategori;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.KategoriSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by msevim on 19.03.2014.
 */
public class KategoriCriteria {

    KategoriSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Kategori> prepareResult() {

        Criteria cr = session.createCriteria(Kategori.class, "k");
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);
        cr.add(Restrictions.eq("k.durum.id", Durum.getAktifObject().getId()));

        if (sorguKriteri.getKategoriAdi() != null) {
            cr.add(Restrictions.like("k.aciklama", "%" + sorguKriteri.getKategoriAdi() + "%"));
        }

        return cr.list();
    }

    public KategoriCriteria(KategoriSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
