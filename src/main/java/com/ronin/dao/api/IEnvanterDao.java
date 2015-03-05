/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;


import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Envanter;
import com.ronin.model.Kategori;
import com.ronin.model.kriter.EnvanterSorguKriteri;
import com.ronin.model.kriter.KategoriSorguKriteri;

import java.util.List;

/**
 *
 * @author msevim
 */
public interface IEnvanterDao {

    List<Kategori> getKategoriListBySorguKriteri(int first, int pageSize, KategoriSorguKriteri sorguKriteri, SessionInfo sessionInfo);

    List<Envanter> getEnvanterListBySorguKriteri(int first, int pageSize, EnvanterSorguKriteri sorguKriteri, SessionInfo sessionInfo);

    void save(Object object);

    void envanterSilme(SessionInfo sessionInfo, Envanter kategori);

     void envanterGuncelleme(SessionInfo sessionInfo, Envanter kategori);

     void kategoriEkleme(SessionInfo sessionInfo, Kategori kategori);

     void kategoriSilme(SessionInfo sessionInfo, Kategori kategori);

     Envanter getEnvanterByBarkodNo(String barkodNo);

}
