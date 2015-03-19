/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IKullaniciDAO;
import com.ronin.common.model.Kullanici;
import com.ronin.dao.api.IDaireDao;
import com.ronin.model.Borc;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.kriter.DaireSorguKriteri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author esimsek
 */
@Service(value = "daireService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DaireService implements IDaireService {

    @Autowired
    IDaireDao iDaireDao;

    @Autowired
    IKullaniciDAO kullaniciDAO;

    @Transactional(readOnly = false)
    public Daire getSingle(Daire arac) {
        return iDaireDao.getSingle(arac);
    }

    @Transactional(readOnly = false)
    public Daire getSingleById(Long id) {
        return iDaireDao.getSingleById(id);
    }

    @Transactional(readOnly = false)
    public Daire getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        return iDaireDao.getSingleOneByNamedQuery(namedQuery, parameters);
    }

    @Transactional(readOnly = false)
    public List<Daire> getList(Daire arac) {
        return iDaireDao.getList(arac);
    }

    @Transactional(readOnly = false)
    public List<Daire> getListByNamedQuery(String namedQuery, Object... parameters) {
        return iDaireDao.getListByNamedQuery(namedQuery, parameters);
    }

    @Transactional(readOnly = false)
    public List<Daire> getListCriteriaForPaging(int first, int pageSize, DaireSorguKriteri sorguKriteri , SessionInfo sessionInfo) {
        return iDaireDao.getListCriteriaForPaging(first, pageSize, sorguKriteri , sessionInfo);
    }

    @Transactional(readOnly = false)
    public int getCountByCriteriaForPaging(DaireSorguKriteri sorguKriteri) {
        return iDaireDao.getCountByCriteriaForPaging(sorguKriteri);
    }

    @Transactional(readOnly = true)
    public List<DaireBorc> getBorcListByDaire(Daire daire) {
        List<DaireBorc> dbList1 = iDaireDao.getBorcListByDaire(daire);
        List<DaireBorc> dbList =  getFilteredBorcList(dbList1);


        if (dbList != null && !dbList.isEmpty()) {
            for (DaireBorc db : dbList) {
                db.getBorc().islendiMi = true;
                db.getBorc().setBakiye(getCurrentBakiye(db, dbList));
            }
        }
        return dbList;
    }

    public List<DaireBorc> getFilteredBorcList(List<DaireBorc> oldBorcList) {
        LinkedHashSet<Date> blackList = new LinkedHashSet<>();
        List<DaireBorc> newDaireBorcList = new ArrayList<>();
        LinkedHashMap<Date, DaireBorc> linkDBlist = new LinkedHashMap<>();
        DecimalFormat df = new DecimalFormat("#.##");
        for (DaireBorc db : oldBorcList) {
            blackList.add(db.getBorc().getIslemTarihi());
        }

        for (Date date : blackList) {
            DaireBorc tempDb = null;
            for (DaireBorc db : oldBorcList) {
                if (date.equals(db.getBorc().getIslemTarihi()) && !db.getBorc().getIslemTipi().isBorcDekontuMu()) {
                    if (linkDBlist.get(date) != null) {
                        tempDb = linkDBlist.get(date);
                        tempDb.getBorc().setOdenenTutar(Double.parseDouble(df.format(tempDb.getBorc().getOdenenTutar() + db.getBorc().getOdenenTutar()).replace(",", ".")));
                        linkDBlist.get(date).setBorc(tempDb.getBorc());
                    } else {
                        db.getBorc().setDekontNo(null);
                        db.getBorc().setSonOdemeTarihi(null);
                        linkDBlist.put(date, db);
                    }
                } else if (date.equals(db.getBorc().getIslemTarihi()) && db.getBorc().getIslemTipi().isBorcDekontuMu()) {
                    db.getBorc().setOdemeVadesi(null);
                    newDaireBorcList.add(db);
                }
            }
            Collection Dblist = linkDBlist.values();
            newDaireBorcList.addAll(Dblist);
            linkDBlist.clear();
        }
        return newDaireBorcList;
    }

    public Double getCurrentBakiye(DaireBorc daireBorc, List<DaireBorc> daireBorcList) {
        Double totalBakiye = 0.0;
        Double tBorc = 0.0;
        Double tAlacak = 0.0;
        for (DaireBorc db : daireBorcList) {
            if ((db.getBorc().getIslemTarihi().before(daireBorc.getBorc().getIslemTarihi()) ||
                    db.getBorc().getIslemTarihi().equals(daireBorc.getBorc().getIslemTarihi()) )&& db.getBorc().islendiMi == false ) {
                tBorc += db.getBorc().getBorc() != null ? db.getBorc().getBorc() : 0.0;
                tAlacak += db.getBorc().getOdenenTutar() != null ? db.getBorc().getOdenenTutar() : 0.0;
            }
        }
       tAlacak+= daireBorc.getBorc().getOdenenTutar() != null ? daireBorc.getBorc().getOdenenTutar() : 0.0;
        tBorc+=  daireBorc.getBorc().getBorc() != null ? daireBorc.getBorc().getBorc() : 0.0;
        totalBakiye = tAlacak - tBorc;
        DecimalFormat df = new DecimalFormat("#.##");
        totalBakiye = Double.parseDouble(df.format(totalBakiye).replace(",", "."));
        return totalBakiye;
    }

    @Transactional(readOnly = false)
    public List<KullaniciDaire> getKullaniciListByDaire(Daire daire) {
        List<Kullanici> kullaniciList = new ArrayList<>();
        List<KullaniciDaire> kullaniciDaireList = kullaniciDAO.getKullaniciListByDaire(daire);
        return kullaniciDaireList;
    }

    @Transactional(readOnly = false)
    public Borc getBakiyeOfDaire(Daire daire) {
        return iDaireDao.getBakiyeOfDaire(daire);
    }

    @Transactional(readOnly = false)
    public int getCount(Daire arac) {
        return iDaireDao.getCount(arac);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void add(Daire arac) {
        iDaireDao.add(arac);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Daire arac) {
        iDaireDao.update(arac);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Daire arac) {
        iDaireDao.delete(arac);
    }

    public boolean isDaireListedeVarMi(List<Daire> daireList, Daire daire) {
        for (Daire d : daireList) {
            if (d.isDaireAynimiWithBlok(daire))
                return true;
        }
        return false;
    }

    public List<Daire> deleteTempDaire(List<Daire> daireList, Daire daire) {
        daireList.remove(daire);
        return daireList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addDaireListToBlok(SessionInfo sessionInfo, List<Daire> daireList){
        iDaireDao.addDaireListToBlok(sessionInfo,daireList);
    }

    public IKullaniciDAO getKullaniciDAO() {
        return kullaniciDAO;
    }

    public void setKullaniciDAO(IKullaniciDAO kullaniciDAO) {
        this.kullaniciDAO = kullaniciDAO;
    }

    public IDaireDao getiDaireDao() {
        return iDaireDao;
    }

    public void setiDaireDao(IDaireDao iDaireDao) {
        this.iDaireDao = iDaireDao;
    }
}
