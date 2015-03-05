package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.TalepDaire;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.kriter.TalepSorguKriteri;
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
public class TalepCriteria {

    TalepSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<TalepDaire> prepareResult() {
        boolean isKey = false;
        Criteria cr = session.createCriteria(TalepDaire.class, "td");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);
        cr.createAlias("td.talep", "t", JoinType.INNER_JOIN);
        cr.createAlias("td.daire", "d", JoinType.LEFT_OUTER_JOIN);
        cr.createAlias("d.blok", "b", JoinType.LEFT_OUTER_JOIN);
        cr.createAlias("t.kullanici", "k", JoinType.INNER_JOIN);
        cr.createAlias("t.talepTipi", "tt", JoinType.INNER_JOIN);
        cr.createAlias("t.talepOnayDurumu", "tod", JoinType.INNER_JOIN);

        cr.add(Restrictions.eq("td.durum.id", Durum.getAktifObject().getId()));
        cr.add(Restrictions.eq("t.sirket.id", sessionInfo.getSirket().getId()));

        if (sorguKriteri.getTalepNo() != null) {
            cr.add(Restrictions.eq("t.id", sorguKriteri.getTalepNo()));
            isKey = true;
        }
        if (!isKey) {

            if (!StringUtils.isEmpty(sorguKriteri.getOnayDurumu())) {
                if (sorguKriteri.getOnayDurumu().equals("ilkGiris")) {
                    cr.add(Restrictions.eq("tod.talepYapildiMi.id", EvetHayir.getEvetObject().getId()));
                } else if (sorguKriteri.getOnayDurumu().equals("onaylandi")) {
                    cr.add(Restrictions.eq("tod.onaylandiMi.id", EvetHayir.getEvetObject().getId()));
                } else if (sorguKriteri.getOnayDurumu().equals("reddedildi")) {
                    cr.add(Restrictions.eq("tod.reddedildiMi.id", EvetHayir.getEvetObject().getId()));
                }
            }

            if (sorguKriteri.getTalepOnayDurumu() != null) {
                cr.add(Restrictions.eq("tod.id", sorguKriteri.getTalepOnayDurumu().getId()));
            }

            if (sorguKriteri.getTalepTipi() != null) {
                cr.add(Restrictions.eq("tt.id", sorguKriteri.getTalepTipi().getId()));
            } else {
                if (sorguKriteri.getTalepTipiList() != null)
                    cr.add(Restrictions.in("t.talepTipi", sorguKriteri.getTalepTipiList()));
            }

            if (sorguKriteri.getKullanici() != null) {
                cr.add(Restrictions.eq("k.id", sorguKriteri.getKullanici().getId()));
            }

            if (sorguKriteri.getDaireNo() != null) {
                cr.add(Restrictions.eq("d.daireNo", sorguKriteri.getDaireNo()));
            }

            if (sorguKriteri.getDaireKodu() != null) {
                cr.add(Restrictions.eq("d.daireKodu", sorguKriteri.getDaireKodu()));
            }

            if (sorguKriteri.getBlok() != null) {
                cr.add(Restrictions.eq("b.id", sorguKriteri.getBlok().getId()));
            }

            if (!StringUtils.isEmpty(sorguKriteri.getBlokAdi())) {
                cr.add(Restrictions.like("b.aciklama", "%" + sorguKriteri.getBlokAdi() + "%"));
            }

            if (sorguKriteri.getSorguBaslangicTarihi() != null) {
                cr.add(Restrictions.ge("t.islemTarihi", sorguKriteri.getSorguBaslangicTarihi()));
            }

            if (sorguKriteri.getSorguBitisTarihi() != null) {
                cr.add(Restrictions.le("t.islemTarihi", sorguKriteri.getSorguBitisTarihi()));
            }
        }
        cr.addOrder(Order.desc("t.islemTarihi"));
        return cr.list();

    }


    public TalepCriteria(TalepSorguKriteri sorguKriteri, SessionInfo sessionInfo , Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
