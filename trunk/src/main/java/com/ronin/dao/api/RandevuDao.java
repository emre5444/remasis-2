/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Randevu;
import com.ronin.model.constant.RandevuTipi;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class RandevuDao implements IRandevuDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<Randevu> getRandevuList(RandevuTipi randevuTipi, Date startDate, Date endDate, SessionInfo sessionInfo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Randevu r where r.baslangicZamani >= ? and r.bitisZamani <= ? and r.randevuTipi.id = ? and r.sirket.id = ?")
                .setParameter(0, startDate)
                .setParameter(1, endDate)
                .setParameter(2, randevuTipi.getId())
                .setParameter(3, sessionInfo.getSirket().getId())
                .list();
        return (List<Randevu>) list;
    }

    public Long hasAktifRandevu(RandevuTipi randevuTipi, Date startDate, Date endDate, SessionInfo sessionInfo) {
        List list = getSessionFactory().getCurrentSession()
                .createQuery("select count(r.id) from Randevu r where r.baslangicZamani >= ? and r.bitisZamani <= ? and r.randevuTipi.id = ? and r.sirket.id = ?")
                .setParameter(0, startDate)
                .setParameter(1, endDate)
                .setParameter(2, randevuTipi.getId())
                .setParameter(3, sessionInfo.getSirket().getId())
                .list();
        return  (Long)list.get(0);
    }


}
