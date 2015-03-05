/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.DaireBelge;
import com.ronin.model.Talep;
import com.ronin.model.constant.Belge;
import com.ronin.model.kriter.BelgeSorguKriteri;
import com.ronin.model.kriter.DaireSorguKriteri;
import org.primefaces.model.UploadedFile;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author esimsek
 */
public interface IFileUploadService {

    void belgeEkle(SessionInfo sessionInfo , UploadedFile uploadedFile , Belge belge);

    void daireBelgeEkle(SessionInfo sessionInfo , Daire daire , UploadedFile uploadedFile , Belge belge);

    void daireTalepBelgeEkle(SessionInfo sessionInfo , Daire daire , Talep talep , UploadedFile uploadedFile , Belge belge);

    List<Belge> getAdminBelgeList(SessionInfo sessionInfo);

    List<DaireBelge> getDaireBelgeList(Daire daire);

    List<DaireBelge> getBelgeListByTalep(Talep talep);

    void belgeSil(DaireBelge daireBelge);

    List<DaireBelge> getBelgeListBySorguSonucu(int first, int pageSize, BelgeSorguKriteri sorguKriteri ,SessionInfo sessionInfo);

}
