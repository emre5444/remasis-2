/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.*;
import com.ronin.model.constant.TalepTipi;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.TalepSorguKriteri;

import java.util.List;

/**
 *
 * @author esimsek
 */
public interface ITalepDao {

    TalepDaire getSingleById(Long id);

     TalepTipi getSingleTalepTipiByEnum(TalepTipi.ENUM talepEnum);

    TalepTipi getSingleOneByNamedQuery(String namedQuery, Object... parameters);

    List<TalepTipi> getListByNamedQuery(String namedQuery, Object... parameters);

    List<TalepDaire> getListCriteriaForPaging(int first, int pageSize, TalepSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    List<TalepDaire> getTalepListByDaire(Daire daire);

    int getCountTalep(Daire daire);

    void addTalep(SessionInfo sessionInfo ,Talep talep);

    void updateTalep(SessionInfo sessionInfo , Talep talep);

    void deleteTalep(TalepDaire talepDaire);

    void talepOnayla(TalepDaire talepDaire);

    void talepReddet(TalepDaire talepDaire);

    void arizaTalebiEkleme(ArizaTalebi talep , Daire daire);

    void sikayetTalebiEkleme(SikayetTalebi talep , Daire daire);

    void belgeTalebiEkleme(BelgeTalebi talep , Daire daire);

    void itirazTalebiEkleme(ItirazTalebi talep , Daire daire);

    DaireBorc getItirazEdilenDaireBorc(DaireBorc daireBorc);
}
