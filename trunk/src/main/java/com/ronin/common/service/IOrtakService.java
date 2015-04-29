/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Il;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.model.*;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.*;
import com.ronin.model.kriter.AnketSorguKriteri;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import com.ronin.model.kriter.IlanSorguKriteri;
import com.ronin.model.sorguSonucu.AnketSonucViewBean;

import java.util.Date;
import java.util.List;

/**
 * @author ealtun
 */
public interface IOrtakService {

    IAbstractEntity getEntityByClass(Class cls, Long entityId);

    IAbstractEntity getSingleOneByNamedQuery(String namedQuery, Object... parameters);

    List<IAbstractEntity> getListByNamedQuery(String namedQuery, Object... parameters);

    List<IAbstractEntity> getListByNamedQuery(String namedQuery);

    List<IAbstractEntity> getListByNamedQueryWithSirket(String namedQuery,SessionInfo sessionInfo);

    List<IAbstractEntity> getIlceListByNamedQueryWithIl(String namedQuery, Il il);

    Durum getSingleDurumEntity(Long id);

    KullaniciSirket getSingleKullaniciSirketEntity(Long id);

    Sirket getSingleSirketEntity(Long id);

    Rol getSingleRolEntity(Long id);

    AnketSecim getSingleAnketSecimEntity(Long id);

    Kategori getSingleKategoriEntity(Long id);

    Integer numberOfSirket();

    List<Duyuru> getAllDuyuruList(SessionInfo sessionInfo);

    List<Duyuru> getAllIlanList(SessionInfo sessionInfo);

    List<Duyuru> getAllDuyuruListByDaire(Daire daire);

    List<Kullanici> getKullaniciByName(String name , SessionInfo sessionInfo);

    KullaniciSecim getKullaniciSecimByKey(Long id,String key);

    void yeniDuyuruEkle(SessionInfo sessionInfo ,Duyuru duyuru);

    void yeniAnketEkle(SessionInfo sessionInfo ,Anket anket , List<AnketSecim> anketSecimList);

    void anketGuncelle(Anket anket , List<AnketSecim> anketSecimList);

    List<AnketSecim> addSecenekToAnket(SessionInfo sessionInfo , List<AnketSecim> anketSecimList , Anket anket , String secim);

    List<AnketSecim> deleteTempSecimToAnket(List<AnketSecim> anketSecimList , AnketSecim anketSecim);

    List<AnketSecim> getAnketSecimListFromAnket(Anket anket);

    List<AnketKullanici> getKullaniciAnketListByAnket(Anket anket);

    void anketeKatilimEkleme(SessionInfo sessionInfo , Anket anket , AnketSecim anketSecim,String aciklama);

    List<AnketSonucViewBean> getAnketSonucDurum(SessionInfo sessionInfo , Anket anket);

    EvetHayir isAnketeKatinildiMi(SessionInfo sessionInfo , Anket anket);

    void deleteAnket(Anket anket);

    void update(Duyuru duyuru);

    void deleteDuyuru(Duyuru duyuru);

    void deleteBelge(Belge belge);

    String getMD5String(String text);

    void bildirimIstekOlustur(SessionInfo sessionInfo, Kullanici kullanici, BildirimTipi.ENUM bildirimTipiEnum, String message, String kisaAciklama,BilgilendirmeTipi.ENUM bilgilendirmeTipiEnum);

    void createErisimLog(SessionInfo sessionInfo ,Kullanici kullanici, LogTipi logTipi, String aciklama);

    Integer numberOfVisitors(SessionInfo sessionInfo ,Date baslangicTarihi);

    List<Duyuru> getDuyuruListBySorguSonucu(int first, int pageSize, DuyuruSorguKriteri sorguKriteri ,SessionInfo sessionInfo);

    List<Duyuru> getIlanListBySorguSonucu(int first, int pageSize, IlanSorguKriteri sorguKriteri ,SessionInfo sessionInfo);

    List<Anket> getAnketListBySorguSonucu(int first, int pageSize, AnketSorguKriteri sorguKriteri ,SessionInfo sessionInfo);

    List<Notification> getAllNotificationList(SessionInfo sessionInfo,int limit);

    void update(Object object);

    void save(Object object);

    List<IletisimBilgileri> getAllIletisimBilgileriBySirket(SessionInfo sessionInfo);

    void updateIletisimBilgileri(IletisimBilgileri iletisimBilgileri);

    void deleteIletisimBilgisi(IletisimBilgileri iletisimBilgileri);

    void iletisimBilgisiEkle(SessionInfo sessionInfo , IletisimBilgileri iletisimBilgileri);

    void createHataLog(SessionInfo sessionInfo , String message , String stachTrace);

    AnketKullanici getAnketKullanici (Anket anket, Long kullaniciId );
}
