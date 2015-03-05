/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.dao.api.IEnvanterDao;
import com.ronin.model.Envanter;
import com.ronin.model.Kategori;
import com.ronin.model.kriter.EnvanterSorguKriteri;
import com.ronin.model.kriter.KategoriSorguKriteri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;


@Service(value = "envanterService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EnvanterService implements IEnvanterService {

    @Autowired
    IEnvanterDao envanterDao;

    @Transactional(readOnly = false)
    public List<Kategori> getKategoriListBySorguKriteri(int first, int pageSize, KategoriSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        return envanterDao.getKategoriListBySorguKriteri(first, pageSize, sorguKriteri, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<Envanter> getEnvanterListBySorguKriteri(int first, int pageSize, EnvanterSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        return envanterDao.getEnvanterListBySorguKriteri(first, pageSize, sorguKriteri, sessionInfo);
    }

    @Transactional(readOnly = false)
    public void save(Object object) {
        envanterDao.save(object);
    }


    @Transactional(readOnly = false)
    public void envanterSilme(SessionInfo sessionInfo, Envanter kategori) {
        envanterDao.envanterSilme(sessionInfo, kategori);
    }

    @Transactional(readOnly = false)
    public void envanterGuncelleme(SessionInfo sessionInfo, Envanter kategori) {
        envanterDao.envanterGuncelleme(sessionInfo, kategori);
    }

    @Transactional(readOnly = false)
    public void kategoriEkleme(SessionInfo sessionInfo, Kategori kategori) {
        envanterDao.kategoriEkleme(sessionInfo, kategori);
    }

    @Transactional(readOnly = false)
    public void kategoriSilme(SessionInfo sessionInfo, Kategori kategori) {
        envanterDao.kategoriSilme(sessionInfo, kategori);
    }

    public String barkodNoGenerator(int lenght) {
        String AB = "0123456789";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(lenght);
        for (int i = 0; i < lenght; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    @Transactional(readOnly = true)
    public Envanter getEnvanterByBarkodNo(String barkodNo){
        return envanterDao.getEnvanterByBarkodNo(barkodNo);
    }

    public IEnvanterDao getEnvanterDao() {
        return envanterDao;
    }

    public void setEnvanterDao(IEnvanterDao envanterDao) {
        this.envanterDao = envanterDao;
    }
}
