/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IOrtakDao;
import com.ronin.common.model.Kullanici;
import com.ronin.dao.api.IDaireDao;
import com.ronin.dao.api.ITalepDao;
import com.ronin.model.*;
import com.ronin.model.constant.TalepOnayDurumu;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.TalepSorguKriteri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author esimsek
 */
@Service(value = "talepService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TalepService implements ITalepService {

    @Autowired
    ITalepDao iITalepDao;

    @Autowired
    IOrtakDao iOrtakDao;

    @Transactional(readOnly = false)
    public TalepDaire getSingleById(Long id) {
        return iITalepDao.getSingleById(id);
    }

    @Transactional(readOnly = false)
    public TalepTipi getSingleTalepTipiByEnum(TalepTipi.ENUM talepEnum){
        return iITalepDao.getSingleTalepTipiByEnum(talepEnum);
    }

    @Transactional(readOnly = false)
    public TalepTipi getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        return iITalepDao.getSingleOneByNamedQuery(namedQuery, parameters);
    }

    @Transactional(readOnly = false)
    public List<TalepTipi> getListByNamedQuery(String namedQuery, Object... parameters) {
        return iITalepDao.getListByNamedQuery(namedQuery, parameters);
    }

    @Transactional(readOnly = false)
    public List<TalepDaire> getListCriteriaForPaging(int first, int pageSize, TalepSorguKriteri sorguKriteri ,SessionInfo sessionInfo) {
        return iITalepDao.getListCriteriaForPaging(first, pageSize, sorguKriteri , sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<TalepDaire> getTalepListByDaire(Daire daire) {
        return iITalepDao.getTalepListByDaire(daire);
    }

    @Transactional(readOnly = false)
    public int getCountTalep(Daire arac) {
        return iITalepDao.getCountTalep(arac);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addTalep(SessionInfo sessionInfo ,Talep talep) {
        iITalepDao.addTalep(sessionInfo ,talep);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateTalep(SessionInfo sessionInfo ,Talep talep) {
        iITalepDao.updateTalep(sessionInfo,talep);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteTalep(TalepDaire talepDaire) {
        iITalepDao.deleteTalep(talepDaire);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void talepOrtakOnayla(TalepDaire talepDaire , SessionInfo sessionInfo) {
        Talep talep = talepDaire.getTalep();
        talep.setOnaylayanKullanici(sessionInfo.getKullanici());
        talep.setOnayTarihi(new Date());
        if(talep instanceof ArizaTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.ARIZA_TALEBI_ONAYLANDI));
        } else if(talep instanceof ItirazTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.ITIRAZ_TALEBI_ONAYLANDI));
        } else if(talep instanceof SikayetTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.SIKAYET_TALEBI_ONAYLANDI));
        } else if(talep instanceof BelgeTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.BELGE_TALEBI_ONAYLANDI));
        }
        iITalepDao.updateTalep(sessionInfo ,talep);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void talepOrtakReddet(TalepDaire talepDaire , SessionInfo sessionInfo) {
        Talep talep = talepDaire.getTalep();
        talep.setOnaylayanKullanici(sessionInfo.getKullanici());
        talep.setOnayTarihi(new Date());
        if(talep instanceof ArizaTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.ARIZA_TALEBI_REDDEDILDI));
        } else if(talep instanceof ItirazTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.ITIRAZ_TALEBI_REDDEDILDI));
        } else if(talep instanceof SikayetTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.SIKAYET_TALEBI_REDDEDILDI));
        } else if(talep instanceof BelgeTalebi){
            talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.BELGE_TALEBI_ONAYLANDI));
        }
        iITalepDao.updateTalep(sessionInfo,talep);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void arizaTalebiEkleme(Daire daire , ArizaTalebi talep, SessionInfo sessionInfo) {
        talep.setKullanici(sessionInfo.getKullanici());
        talep.setIslemTarihi(new Date());
        talep.setTalepTipi(new TalepTipi(TalepTipi.ENUM.ARIZA_TALEBI));
        talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.ARIZA_TALEBI_ONAY_BEKLIYOR));
        talep.setSirket(sessionInfo.getSirket());
        iITalepDao.arizaTalebiEkleme(talep , daire);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void sikayetTalebiEkleme(Daire daire , SikayetTalebi talep, SessionInfo sessionInfo) {
        talep.setKullanici(sessionInfo.getKullanici());
        talep.setIslemTarihi(new Date());
        talep.setTalepTipi(new TalepTipi(TalepTipi.ENUM.SIKAYET_TALEBI));
        talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.SIKAYET_TALEBI_ONAY_BEKLIYOR));
        talep.setSirket(sessionInfo.getSirket());
        iITalepDao.sikayetTalebiEkleme(talep , daire);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void belgeTalebiEkleme(Daire daire , BelgeTalebi talep , SessionInfo sessionInfo){
        talep.setKullanici(sessionInfo.getKullanici());
        talep.setIslemTarihi(new Date());
        talep.setTalepTipi(new TalepTipi(TalepTipi.ENUM.BELGE_TALEBI));
        talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.BELGE_TALEBI_ONAY_BEKLIYOR));
        talep.setSirket(sessionInfo.getSirket());
        iITalepDao.belgeTalebiEkleme(talep , daire);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void itirazTalebiEkleme(Daire daire , ItirazTalebi talep , SessionInfo sessionInfo , DaireBorc daireBorc){
        talep.setDaireBorc(daireBorc);
        talep.setKullanici(sessionInfo.getKullanici());
        talep.setIslemTarihi(new Date());
        talep.setTalepTipi(new TalepTipi(TalepTipi.ENUM.ITIRAZ_TALEBI));
        talep.setTalepOnayDurumu(new TalepOnayDurumu(TalepOnayDurumu.ENUM.ITIRAZ_TALEBI_ONAY_BEKLIYOR));
        talep.setSirket(sessionInfo.getSirket());
        iITalepDao.itirazTalebiEkleme(talep , daire);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DaireBorc getItirazEdilenDaireBorc(DaireBorc daireBorc){
       return iITalepDao.getItirazEdilenDaireBorc(daireBorc);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<DaireBorc> getItirazEdilenDaireBorcAsList(DaireBorc daireBorc){
        List<DaireBorc> dbList = new ArrayList<>();
        DaireBorc db = getItirazEdilenDaireBorc(daireBorc);
        dbList.add(db);
        return dbList;
    }

    public Talep setTalep(SessionInfo sessionInfo, Talep talep) {

        return talep;
    }

}
