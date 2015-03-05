package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.model.kriter.KullaniciSorguKriteri;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by msevim on 17.03.2014.
 */
public class KullaniciCriteria {

    KullaniciSorguKriteri sorguKriteri;

    Session session;
    int first;
    int pageSize;
    SessionInfo sessionInfo;

    public List<Kullanici> prepareResult() {

        Criteria cr = session.createCriteria(Kullanici.class, "k");
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.setFirstResult(first);
        cr.setMaxResults(pageSize);

        cr.createAlias("k.kullaniciDaireList", "kdl" , JoinType.LEFT_OUTER_JOIN);
        cr.createAlias("k.kullaniciSirketList", "ksl" , JoinType.INNER_JOIN);
        cr.createAlias("kdl.daire", "d" , JoinType.LEFT_OUTER_JOIN);
        cr.createAlias("d.blok", "bl" , JoinType.LEFT_OUTER_JOIN);

        cr.add(Restrictions.eq("ksl.sirket.id", sessionInfo.getSirket().getId()));

        if (!StringUtils.isEmpty(sorguKriteri.getAd())) {
            cr.add(Restrictions.like("k.ad", "%" + sorguKriteri.getAd() + "%"));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getSoyad())) {
            cr.add(Restrictions.like("k.soyad", "%" + sorguKriteri.getSoyad() + "%"));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getKullaniciAdi())) {
            cr.add(Restrictions.eq("k.username", sorguKriteri.getKullaniciAdi()));
        }

        if(sorguKriteri.getSistemYoneticisiMi()!=null){
            cr.createAlias("k.kullaniciRolList", "krl" , JoinType.LEFT_OUTER_JOIN);
            cr.createAlias("krl.rol", "r" , JoinType.LEFT_OUTER_JOIN);
            cr.add(Restrictions.eq("r.sistemYoneticisiMi", sorguKriteri.getSistemYoneticisiMi()));
        }

        if(sorguKriteri.getRol()!=null){
            cr.createAlias("k.kullaniciRolList", "krl" , JoinType.LEFT_OUTER_JOIN);
            cr.createAlias("krl.rol", "r" , JoinType.LEFT_OUTER_JOIN);
            cr.add(Restrictions.eq("r.id", sorguKriteri.getRol().getId()));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getBlokAdi())) {
            cr.add(Restrictions.like("bl.aciklama", "%" + sorguKriteri.getBlokAdi() + "%"));
        }

        if(sorguKriteri.getBlok()!=null){
            cr.add(Restrictions.eq("bl.id", sorguKriteri.getBlok().getId()));
        }

        if(sorguKriteri.getDaireNo()!=null){
            cr.add(Restrictions.eq("d.daireNo", sorguKriteri.getDaireNo()));
        }

        if (!StringUtils.isEmpty(sorguKriteri.getEmail())) {
            cr.add(Restrictions.like("k.email", "%" + sorguKriteri.getEmail() + "%"));
        }

        if(sorguKriteri.getDurum()!=null){
            cr.add(Restrictions.eq("k.durum", sorguKriteri.getDurum()));
        }

        return cr.list();
    }

    public KullaniciCriteria(KullaniciSorguKriteri sorguKriteri, SessionInfo sessionInfo ,Session session, int first, int pageSize) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.first = first;
        this.pageSize = pageSize;
        this.sessionInfo = sessionInfo;
    }
}
