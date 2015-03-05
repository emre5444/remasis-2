package com.ronin.model.criteria;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.kriter.RaporSorguKriteri;
import com.ronin.model.sorguSonucu.R101SorguSonucu;
import com.ronin.model.sorguSonucu.R201SorguSonucu;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ealtun on 13.03.2014.
 */
public class R201RaporCriteria {

    RaporSorguKriteri sorguKriteri;
    SessionInfo sessionInfo;
    Session session;

    public List<R201SorguSonucu> prepareResult() {
        List<R201SorguSonucu> resultList = new ArrayList<>();

        String sql = "SELECT  d.daire_kodu, " +
                "        bl.aciklama, " +
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
                "LIMIT 1) AS  sakin, " +
                "       b.dekont_no, " +
                "       date(b.islem_tarihi) as islem_tarihi, " +
                "       date(b.odeme_vadesi) as odeme_vadesi, " +
                "       date(b.son_odeme_tarihi) as son_odeme_tarihi, " +
                "       b.borc, " +
                "       b.aciklama as aidat_aciklama, " +
                "       b.odenen_tutar " +
                "FROM " +
                "    daire_borc db " +
                "    inner join borc b on b.id = db.borc_id " +
                "    inner join daire d on d.id = db.daire_id " +
                "    inner join borc_tipi bt on bt.id = d.borc_tipi_id " +
                "    inner join blok bl on bl.id = d.blok_id" +
                " WHERE bl.sirket_id = :sirket_id ";

        if (sorguKriteri.getBlok() != null) {
            sql+=  "AND bl.id = :blok_ad ";
        }

        if (sorguKriteri.getDaireNo() != null) {
            sql += "AND d.daire_no = :daire_no ";
        }

        if (sorguKriteri.getKullanici() != null) {
            sql += "AND d.id in (select kd.daire_id " +
                    "from kullanici_daire kd " +
                    "WHERE kd.kullanici_id = :kullanici_id )";
        }

        sql += " order by d.daire_kodu asc , b.dekont_no desc, b.islem_tarihi desc ,bt.id desc ";

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

        query.setParameter("sirket_id", sessionInfo.getSirket().getId());


        List data = query.list();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");
        for (Object object : data) {
            try {
                Map row = (Map) object;
                R201SorguSonucu entiy = new R201SorguSonucu();
                entiy.setDaireKodu((String) row.get("daire_kodu"));
                entiy.setBlokAdi((String) row.get("aciklama"));
                entiy.setDaireNo(Integer.parseInt(row.get("daire_no").toString()));
                entiy.setDaireBakiye(new Double(row.get("bakiye").toString()));
                entiy.setMalik((String) row.get("malik"));
                entiy.setSakin((String) row.get("sakin"));
                entiy.setDekontNo(row.get("dekont_no").toString());
                entiy.setIslemTarihi(formatter.parse(row.get("islem_tarihi").toString()));
              //  entiy.setOdemeVadesi(formatter.parse(row.get("odeme_vadesi").toString()));
              //  entiy.setSonOdemeTarihi(formatter.parse(row.get("son_odeme_tarihi").toString()));
                entiy.setBorc(new Double(row.get("borc").toString()));
                entiy.setAciklama(row.get("aidat_aciklama")!= null ? row.get("aidat_aciklama").toString() : "");
                entiy.setOdenenTutar(new Double(row.get("odenen_tutar").toString()));

                resultList.add(entiy);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return resultList;
    }


    public R201RaporCriteria(RaporSorguKriteri sorguKriteri,SessionInfo sessionInfo ,  Session session) {
        this.sorguKriteri = sorguKriteri;
        this.session = session;
        this.sessionInfo = sessionInfo;
    }
}
