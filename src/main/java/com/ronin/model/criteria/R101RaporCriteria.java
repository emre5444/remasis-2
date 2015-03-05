package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.RaporSorguKriteri;
import com.ronin.model.sorguSonucu.R101SorguSonucu;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ealtun on 13.03.2014.
 */
public class R101RaporCriteria {

    RaporSorguKriteri sorguKriteri;
    Session session;
    SessionInfo sessionInfo;

    public List<R101SorguSonucu> prepareResult() {
        List<R101SorguSonucu> resultList = new ArrayList<>();

        String sql = "SELECT  d.daire_kodu, " +
                     "        b.aciklama, " +
                     "        d.daire_no, " +
                     "        d.bakiye, " +
                             "(select concat(k.ad , ' ' , k.soyad) " +
                             "from kullanici_daire kd , kullanici k " +
                             "WHERE kd.kullanici_id = k.id " +
                             "AND kd.daire_id = d.id " +
                             "AND kd.kallanici_tipi_id = 1 " +
                             "LIMIT 1) AS  malik, " +

                            "(select concat(k.ad , ' ' , k.soyad) " +
                            "from kullanici_daire kd , kullanici k " +
                            "WHERE kd.kullanici_id = k.id " +
                            "AND kd.daire_id = d.id " +
                            "AND kd.kallanici_tipi_id = 2 " +
                            "LIMIT 1) AS  sakin " +
                     "FROM daire d , blok b , borc_tipi bt " +
                     "WHERE d.blok_id = b.id " +
                     "AND d.borc_tipi_id = bt.id " +
                     "AND d.bakiye > 0 " +
                     "AND bt.id = 1 ";

        if (sorguKriteri.getBlok() != null) {
            sql+=  "AND b.id = :blok_ad ";
        }

        if (sorguKriteri.getDaireNo() != null) {
           sql+= "AND d.daire_no = :daire_no ";
        }

        if (sorguKriteri.getKullanici() != null) {
           sql+= "AND d.id in (select kd.daire_id " +
                               "from kullanici_daire kd " +
                               "WHERE kd.kullanici_id = :kullanici_id )";
        }

        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        if (sorguKriteri.getDaireNo() != null) {
            query.setParameter("daire_no", sorguKriteri.getDaireNo());
        }

        if (sorguKriteri.getBlok() != null) {
            query.setParameter("blok_ad", sorguKriteri.getBlok().getId());
        }

        if (sorguKriteri.getKullanici() != null) {
            query.setParameter("kullanici_id", sorguKriteri.getKullanici().getId());
        }

        List data = query.list();

        for(Object object : data)
        {
            Map row = (Map)object;
            R101SorguSonucu entiy = new R101SorguSonucu();
            entiy.setDaireKodu((String) row.get("daire_kodu"));
            entiy.setBlokAdi((String)row.get("aciklama"));
            entiy.setDaireNo(Integer.parseInt(row.get("daire_no").toString()));
            entiy.setBakiye(new Double(row.get("bakiye").toString()));
            entiy.setMalik((String) row.get("malik"));
            entiy.setSakin((String) row.get("sakin"));
            resultList.add(entiy);
        }


        return resultList;

    }


    public R101RaporCriteria(RaporSorguKriteri sorguKriteri, SessionInfo sessionInfo ,Session session) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.sessionInfo = sessionInfo;
    }
}
