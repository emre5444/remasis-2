/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.*;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.kriter.TalepSorguKriteri;

import java.util.List;

/**
 *
 * @author ealtun
 */
public interface ITalepService {

    TalepDaire getSingleById(Long id);

    TalepTipi getSingleTalepTipiByEnum(TalepTipi.ENUM talepEnum);

    TalepTipi getSingleOneByNamedQuery(String namedQuery, Object... parameters);

    List<TalepTipi> getListByNamedQuery(String namedQuery, Object... parameters);

    List<TalepDaire> getListCriteriaForPaging(int first, int pageSize, TalepSorguKriteri sorguKriteri ,SessionInfo sessionInfo);

    List<TalepDaire> getTalepListByDaire(Daire daire);
    
    int getCountTalep(Daire talep);

    void addTalep(SessionInfo sessionInfo ,Talep talep);

    void updateTalep(SessionInfo sessionInfo ,Talep talep);

    void deleteTalep(TalepDaire talepDaire);

    void talepOrtakOnayla(TalepDaire talepDaire , SessionInfo sessionInfo);

    void talepOrtakReddet(TalepDaire talepDaire , SessionInfo sessionInfo);

    void arizaTalebiEkleme(Daire daire , ArizaTalebi talep , SessionInfo sessionInfo);

    void sikayetTalebiEkleme(Daire daire , SikayetTalebi talep , SessionInfo sessionInfo);

    void belgeTalebiEkleme(Daire daire , BelgeTalebi talep , SessionInfo sessionInfo);

    void itirazTalebiEkleme(Daire daire , ItirazTalebi talep , SessionInfo sessionInfo, DaireBorc daireBorc);

    List<DaireBorc> getItirazEdilenDaireBorcAsList(DaireBorc daireBorc);

}
