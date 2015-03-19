/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.constant.Blok;
import com.ronin.model.kriter.BlokSorguKriteri;

import java.util.List;

/**
 * @author fcabi
 */
public interface IBlokDao {

    List<Blok> getListCriteriaForPaging(int first, int pageSize, BlokSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    Blok add(SessionInfo  sessionInfo ,Blok blok);

    void update(SessionInfo sessionInfo ,Blok blok);

    void delete(SessionInfo sessionInfo ,Blok blok);

    void addDaireListToBlok(SessionInfo sessionInfo, List<Daire> daireList, Blok blok);

    List<Daire> getDaireListByBlok(Blok blok);

    }
