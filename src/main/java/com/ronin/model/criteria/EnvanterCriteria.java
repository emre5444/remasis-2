package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Envanter;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.EnvanterSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by msevim on 19.03.2014.
 */
public class EnvanterCriteria {

    EnvanterSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Envanter> prepareResult() {

        Criteria cr = session.createCriteria(Envanter.class, "e");
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.add(Restrictions.eq("e.durum.id", Durum.getAktifObject().getId()));

        if (sorguKriteri.getBarkodNo() != null) {
            cr.add(Restrictions.eq("e.barkodNo", sorguKriteri.getBarkodNo()));
        }

        if (sorguKriteri.getBaslangicIlimTarihi() != null) {
            cr.add(Restrictions.ge("e.alimTarihi", sorguKriteri.getBaslangicIlimTarihi()));
        }

        if (sorguKriteri.getBitisIlimTarihi() != null) {
            cr.add(Restrictions.le("e.alimTarihi", sorguKriteri.getBitisIlimTarihi()));
        }

        if (sorguKriteri.getMarka() != null) {
            cr.add(Restrictions.like("e.marka", "%" + sorguKriteri.getMarka() + "%"));
        }

        if (sorguKriteri.getModel() != null) {
            cr.add(Restrictions.like("e.model", "%" + sorguKriteri.getModel() + "%"));
        }

        if (sorguKriteri.getUrunAdi() != null) {
            cr.add(Restrictions.like("e.urunAdi", "%" + sorguKriteri.getUrunAdi() + "%"));
        }

        if (sorguKriteri.getKategori() != null) {
            cr.add(Restrictions.eq("e.kategori.id", sorguKriteri.getKategori().getId()));
        }

        if (sorguKriteri.getSaticiFirma() != null) {
            cr.add(Restrictions.like("e.saticiFirma", "%" + sorguKriteri.getSaticiFirma() + "%"));
        }

        if (sorguKriteri.getZimmetliPersonel() != null) {
            cr.add(Restrictions.like("e.zimmetliPersonel", "%" + sorguKriteri.getZimmetliPersonel() + "%"));
        }

        return cr.list();
    }

    public EnvanterCriteria(EnvanterSorguKriteri sorguKriteri, SessionInfo sessionInfo, Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
