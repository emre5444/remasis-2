/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.constant.Blok;
import com.ronin.model.kriter.BlokSorguKriteri;

import java.util.List;

/**
 * @author fcabi
 */
public interface IBlokService {

    List<Blok> getListCriteriaForPaging(int first, int pageSize, BlokSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    void add(SessionInfo sessionInfo ,Blok blok);

    void update(SessionInfo sessionInfo ,Blok blok);

    void delete(SessionInfo sessionInfo ,Blok blok);

}
