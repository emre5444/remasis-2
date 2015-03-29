/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.common.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IOrtakDao;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.model.*;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.MailSender;
import com.ronin.model.constant.*;
import com.ronin.model.kriter.AnketSorguKriteri;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import com.ronin.model.kriter.IlanSorguKriteri;
import com.ronin.model.kriter.TalepSorguKriteri;
import com.ronin.model.sorguSonucu.AnketSonucViewBean;
import com.ronin.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author esimsek
 */
@Service(value = "ortakService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrtakService implements IOrtakService {

    @Autowired
    IOrtakDao iOrtakDao;

    @Transactional(readOnly = false)
    public IAbstractEntity getEntityByClass(Class cls, Long entityId) {
        return iOrtakDao.getEntityByClass(cls, entityId);
    }

    @Transactional(readOnly = false)
    public IAbstractEntity getSingleOneByNamedQuery(String namedQuery, Object... parameters) {
        return iOrtakDao.getSingleOneByNamedQuery(namedQuery, parameters);
    }


    @Transactional(readOnly = false)
    public List<IAbstractEntity> getListByNamedQuery(String namedQuery, Object... parameters) {
        return iOrtakDao.getListByNamedQuery(namedQuery, parameters);
    }


    @Transactional(readOnly = false)
    public List<IAbstractEntity> getListByNamedQuery(String namedQuery) {
        return iOrtakDao.getListByNamedQuery(namedQuery);
    }

    @Transactional(readOnly = false)
    public List<IAbstractEntity> getListByNamedQueryWithSirket(String namedQuery,SessionInfo sessionInfo) {
        return iOrtakDao.getListByNamedQueryWithSirket(namedQuery,sessionInfo);
    }

    @Transactional(readOnly = false)
    public Durum getSingleDurumEntity(Long id) {
        return iOrtakDao.getSingleDurumEntity(id);
    }

    @Transactional(readOnly = false)
    public KullaniciSirket getSingleKullaniciSirketEntity(Long id){
        return iOrtakDao.getSingleKullaniciSirketEntity(id);
    }

    @Transactional(readOnly = false)
    public Sirket getSingleSirketEntity(Long id){
        return iOrtakDao.getSingleSirketEntity(id);
    }

    @Transactional(readOnly = false)
    public Rol getSingleRolEntity(Long id){
        return iOrtakDao.getSingleRolEntity(id);
    }

    @Transactional(readOnly = false)
    public AnketSecim getSingleAnketSecimEntity(Long id){
        return iOrtakDao.getSingleAnketSecimEntity(id);
    }

    @Transactional(readOnly = false)
    public Kategori getSingleKategoriEntity(Long id){
        return iOrtakDao.getSingleKategoriEntity(id);
    }

    @Transactional(readOnly = false)
    public Integer numberOfSirket(){
        return iOrtakDao.numberOfSirket();
    }

    @Transactional(readOnly = false)
    public List<Duyuru> getAllDuyuruList(SessionInfo sessionInfo) {
        return iOrtakDao.getAllDuyuruList(sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Duyuru> getDuyuruListBySorguSonucu(int first, int pageSize, DuyuruSorguKriteri sorguKriteri ,SessionInfo sessionInfo) {
        return iOrtakDao.getDuyuruListBySorguSonucu(first, pageSize, sorguKriteri, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Duyuru> getIlanListBySorguSonucu(int first, int pageSize, IlanSorguKriteri sorguKriteri ,SessionInfo sessionInfo) {
        return iOrtakDao.getIlanListBySorguSonucu(first,pageSize,sorguKriteri,sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Anket> getAnketListBySorguSonucu(int first, int pageSize, AnketSorguKriteri sorguKriteri ,SessionInfo sessionInfo){
        return iOrtakDao.getAnketListBySorguSonucu(first,pageSize,sorguKriteri,sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Duyuru> getAllIlanList(SessionInfo sessionInfo) {
        return iOrtakDao.getAllIlanList(sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Duyuru> getAllDuyuruListByDaire(Daire daire) {
        return iOrtakDao.getAllDuyuruListByDaire(daire);
    }

    @Transactional(readOnly = false)
    public List<Kullanici> getKullaniciByName(String name , SessionInfo sessionInfo) {
        List<Kullanici> kulList = new ArrayList<>();
        if (!StringUtils.isEmpty(name) && name.length() > 1)
            kulList = iOrtakDao.getKullaniciByName(name , sessionInfo);
        return kulList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void yeniDuyuruEkle(SessionInfo sessionInfo ,Duyuru duyuru) {
        duyuru.setKisaAciklama(duyuru.getKisaAciklama().toUpperCase(new Locale("tr")));
        duyuru.setSirket(sessionInfo.getSirket());
        iOrtakDao.add(duyuru);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void yeniAnketEkle(SessionInfo sessionInfo ,Anket anket , List<AnketSecim> anketSecimList) {
        anket.setSirket(sessionInfo.getSirket());
        anket.setAktifMi(EvetHayir.getEvetObject());
        anket.setTanitimZamani(DateUtils.getToday());
        anket.setKullanici(sessionInfo.getKullanici());
        anket.setDurum(Durum.getAktifObject());
        iOrtakDao.yeniAnketEkle(anket, anketSecimList);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void anketGuncelle(Anket anket , List<AnketSecim> anketSecimList){
        iOrtakDao.anketGuncelle(anket, anketSecimList);
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<AnketSecim> addSecenekToAnket(SessionInfo sessionInfo , List<AnketSecim> anketSecimList , Anket anket , String secim){
        if(anketSecimList == null)
            anketSecimList = new ArrayList<>();
        AnketSecim anketSecim = new AnketSecim();
        anketSecim.setAnket(anket);
        anketSecim.setSecim(secim);
        anketSecim.setTanitimZamani(DateUtils.getToday());
        anketSecimList.add(anketSecim);
        return anketSecimList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<AnketSecim> deleteTempSecimToAnket(List<AnketSecim> anketSecimList , AnketSecim anketSecim){
        anketSecimList.remove(anketSecim);
        return anketSecimList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<AnketSecim> getAnketSecimListFromAnket(Anket anket){
        return iOrtakDao.getAnketSecimListFromAnket(anket);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<AnketKullanici> getKullaniciAnketListByAnket(Anket anket){
       return iOrtakDao.getKullaniciAnketListByAnket(anket);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void anketeKatilimEkleme(SessionInfo sessionInfo , Anket anket , AnketSecim anketSecim , String aciklama){
         iOrtakDao.anketeKatilimEkleme(sessionInfo , anket , anketSecim , aciklama);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<AnketSonucViewBean> getAnketSonucDurum(SessionInfo sessionInfo , Anket anket){
         return iOrtakDao.getAnketSonucDurum(sessionInfo,anket);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public EvetHayir isAnketeKatinildiMi(SessionInfo sessionInfo , Anket anket){
          return iOrtakDao.isAnketeKatinildiMi(sessionInfo,anket);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteAnket(Anket anket) {
        iOrtakDao.deleteAnket(anket);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Duyuru duyuru) {
        iOrtakDao.update(duyuru);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDuyuru(Duyuru duyuru) {
        iOrtakDao.deleteDuyuru(duyuru);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteBelge(Belge belge) {
        iOrtakDao.deleteBelge(belge);
    }

    public String getMD5String(String text) {
        String passwordToHash = text;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void bildirimIstekOlustur(SessionInfo sessionInfo, Kullanici kullanici, BildirimTipi.ENUM bildirimTipiEnum, String message , String kisaAciklama,BilgilendirmeTipi.ENUM bilgilendirmeTipiEnum) {
        BildirimTipi bildirimTipi = new BildirimTipi(bildirimTipiEnum);
        BilgilendirmeTipi bilgilendirmeTipi =new BilgilendirmeTipi(bilgilendirmeTipiEnum);
        if (kullanici == null) {
            BildirimIstek bildirimIstek = new BildirimIstek();
            bildirimIstek.setDurum(Durum.getAktifObject());
            bildirimIstek.setMesaj(message);
            bildirimIstek.setIslemTarihi(new Date());
            bildirimIstek.setBildirimTipi(bildirimTipi);
            bildirimIstek.setSirket(sessionInfo.getSirket());
            bildirimIstek.setKisaAciklama(kisaAciklama);
            bildirimIstek.setBilgilendirmeTipi(bilgilendirmeTipi);
            iOrtakDao.mailGonder(bildirimIstek);
        } else {
            BildirimIstek bildirimIstek = new BildirimIstek();
            bildirimIstek.setDurum(Durum.getAktifObject());
            bildirimIstek.setMesaj(message);
            bildirimIstek.setIslemTarihi(new Date());
            bildirimIstek.setBildirimTipi(bildirimTipi);
            bildirimIstek.setKullanici(kullanici);
            bildirimIstek.setSirket(sessionInfo.getSirket());
            bildirimIstek.setKisaAciklama(kisaAciklama);
            bildirimIstek.setBilgilendirmeTipi(bilgilendirmeTipi);
            iOrtakDao.mailGonder(bildirimIstek);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createErisimLog(SessionInfo sessionInfo ,Kullanici kullanici, LogTipi logTipi, String aciklama) {
        iOrtakDao.createErisimLog(sessionInfo ,kullanici, logTipi, aciklama);
    }

    public IOrtakDao getiOrtakDao() {
        return iOrtakDao;
    }

    public void setiOrtakDao(IOrtakDao iOrtakDao) {
        this.iOrtakDao = iOrtakDao;
    }

    @Transactional(readOnly = false)
    public  List<Notification> getAllNotificationList(SessionInfo sessionInfo,int limit) {
        return iOrtakDao.getAllNotificationList(sessionInfo,limit);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Object object) {
        iOrtakDao.update(object);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<IletisimBilgileri> getAllIletisimBilgileriBySirket(SessionInfo sessionInfo) {
       return iOrtakDao.getAllIletisimBilgileriBySirket(sessionInfo);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateIletisimBilgileri(IletisimBilgileri iletisimBilgileri){
         iOrtakDao.updateIletisimBilgileri(iletisimBilgileri);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteIletisimBilgisi(IletisimBilgileri iletisimBilgileri){
        iOrtakDao.deleteIletisimBilgisi(iletisimBilgileri);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
   public void iletisimBilgisiEkle(SessionInfo sessionInfo , IletisimBilgileri iletisimBilgileri){
        iletisimBilgileri.setSirket(sessionInfo.getSirket());
         iOrtakDao.iletisimBilgisiEkle(sessionInfo,iletisimBilgileri);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createHataLog(SessionInfo sessionInfo , String message , String stachTrace){
        iOrtakDao.createHataLog(sessionInfo , message , stachTrace);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public AnketKullanici getAnketKullanici (Anket anket, Long kullaniciId ){
        return iOrtakDao.getAnketKullanici(anket, kullaniciId);
    }

}
