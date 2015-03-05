/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.dao.api.IDaireDao;
import com.ronin.dao.api.IFileUploadDao;
import com.ronin.model.Daire;
import com.ronin.model.DaireBelge;
import com.ronin.model.Duyuru;
import com.ronin.model.Talep;
import com.ronin.model.constant.Belge;
import com.ronin.model.kriter.BelgeSorguKriteri;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esimsek
 */
@Service(value = "fileUploadService")
@Transactional(readOnly =true, propagation = Propagation.SUPPORTS)
public class FileUploadService implements IFileUploadService {
    
    @Autowired
    IFileUploadDao iFileUploadDao;

    @Transactional(readOnly = false)
   public void belgeEkle(SessionInfo sessionInfo , UploadedFile uploadedFile , Belge belge){
        iFileUploadDao.belgeEkle(sessionInfo , uploadedFile , belge);
    }

    @Transactional(readOnly = false)
    public void daireBelgeEkle(SessionInfo sessionInfo , Daire daire , UploadedFile uploadedFile , Belge belge){
        iFileUploadDao.daireBelgeEkle(sessionInfo, daire, uploadedFile , belge);
    }

    @Transactional(readOnly = false)
    public void daireTalepBelgeEkle(SessionInfo sessionInfo , Daire daire , Talep talep , UploadedFile uploadedFile , Belge belge){
        iFileUploadDao.daireTalepBelgeEkle(sessionInfo,daire,talep,uploadedFile , belge);
    }

    @Transactional(readOnly = false)
    public List<Belge> getAdminBelgeList(SessionInfo sessionInfo){
       return iFileUploadDao.getAdminBelgeList(sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<DaireBelge> getDaireBelgeList(Daire daire){
        return iFileUploadDao.getDaireBelgeList(daire);
    }

    @Transactional(readOnly = false)
    public List<DaireBelge> getBelgeListByTalep(Talep talep){
        return iFileUploadDao.getBelgeListByTalep(talep);
    }

    @Transactional(readOnly = false)
    public void belgeSil(DaireBelge daireBelge){
         iFileUploadDao.belgeSil(daireBelge);
    }

    @Transactional(readOnly = false)
    public List<DaireBelge> getBelgeListBySorguSonucu(int first, int pageSize, BelgeSorguKriteri sorguKriteri ,SessionInfo sessionInfo) {
        return iFileUploadDao.getBelgeListBySorguSonucu(first, pageSize, sorguKriteri, sessionInfo);
    }




}
