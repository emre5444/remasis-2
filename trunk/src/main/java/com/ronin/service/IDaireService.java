/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.model.*;
import com.ronin.model.kriter.DaireSorguKriteri;

import java.util.List;

/**
 *
 * @author ealtun
 */
public interface IDaireService {

    Daire getSingle(Daire arac);
    
    Daire getSingleById(Long id);
    
    Daire getSingleOneByNamedQuery(String namedQuery, Object... parameters);
    
    List<Daire> getList(Daire arac);
    
    List<Daire> getListByNamedQuery(String namedQuery, Object... parameters);

    List<Daire> getListCriteriaForPaging(int first, int pageSize,DaireSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    int getCountByCriteriaForPaging(DaireSorguKriteri sorguKriteri);

    List<DaireBorc> getBorcListByDaire(Daire daire);

    List<KullaniciDaire> getKullaniciListByDaire(Daire daire);

    List<KullaniciDaire> getVarsayilanKullaniciListByDaire(Daire daire);

    Borc getBakiyeOfDaire(Daire daire);
    
    int getCount(Daire arac);

    void add(Daire arac);

    void update(Daire arac);

    void delete(Daire arac);

    boolean isDaireListedeVarMi(List<Daire> daireList, Daire daire);

    List<Daire> deleteTempDaire(List<Daire> daireList, Daire daire);

    void addDaireListToBlok(SessionInfo sessionInfo, List<Daire> daireList);

    void daireSakinEkleme(DaireSakin daireSakin , SessionInfo sessionInfo);

    void daireSakinGuncelleme(DaireSakin daireSakin , SessionInfo sessionInfo);

    void daireSakinSilme(DaireSakin daireSakin , SessionInfo sessionInfo);

    List<DaireSakin> getDaireSakinListByDaire(Daire daire , Kullanici kullanici);

}
