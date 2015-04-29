/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.DaireBelge;
import com.ronin.model.Duyuru;
import com.ronin.model.Talep;
import com.ronin.model.constant.Belge;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import com.ronin.model.criteria.BelgeCriteria;
import com.ronin.model.criteria.DaireBelgeCriteria;
import com.ronin.model.criteria.DaireCriteria;
import com.ronin.model.criteria.DuyuruCriteria;
import com.ronin.model.kriter.BelgeSorguKriteri;
import com.ronin.model.kriter.DaireSorguKriteri;
import com.ronin.model.kriter.DuyuruSorguKriteri;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author esimsek
 */
@Repository
public class FileUploadDao implements IFileUploadDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void belgeEkle(SessionInfo sessionInfo , UploadedFile uploadedFile , Belge belge){
        try {
            saveFile(sessionInfo, uploadedFile, belge);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Belge saveFile(SessionInfo sessionInfo, UploadedFile uploadedFile, Belge belge) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("C:\\rmsFileUploadDocument\\" + uploadedFile.getFileName());
        belge.setContent(Hibernate.getLobCreator(getSessionFactory().getCurrentSession()).createBlob(inputStream , uploadedFile.getSize()));
        belge.setDataName(uploadedFile.getFileName());
        belge.setKullanici(sessionInfo.getKullanici());
        belge.setSize(uploadedFile.getSize());
        belge.setIslemTarihi(new Date());
        belge.setSirket(sessionInfo.getSirket());
        getSessionFactory().getCurrentSession().save(belge);
        return belge;
    }

    public void daireBelgeEkle(SessionInfo sessionInfo , Daire daire , UploadedFile uploadedFile , Belge belge){
        try {
            belge = saveFile(sessionInfo, uploadedFile, belge);
            DaireBelge daireBelge = new DaireBelge();
            daireBelge.setBelge(belge);
            daireBelge.setDaire(daire);
            getSessionFactory().getCurrentSession().save(daireBelge);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void daireTalepBelgeEkle(SessionInfo sessionInfo , Daire daire , Talep talep , UploadedFile uploadedFile , Belge belge){
        try {
            belge = saveFile(sessionInfo, uploadedFile, belge);
            DaireBelge daireBelge = new DaireBelge();
            daireBelge.setBelge(belge);
            daireBelge.setDaire(daire);
            daireBelge.setTalep(talep);
            getSessionFactory().getCurrentSession().save(daireBelge);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<Belge> getAdminBelgeList(SessionInfo sessionInfo){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from Belge b where not exists (select db from DaireBelge db join db.belge bb where bb.id = b.id and bb.sirket.id = ?) and b.sirket.id =? order by b.id desc")
                .setParameter(0, sessionInfo.getSirket().getId())
                .setParameter(1, sessionInfo.getSirket().getId())
                .setMaxResults(10)
                .list();
        return (List<Belge>) list;
    }

    public List<DaireBelge> getDaireBelgeList(Daire daire){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from DaireBelge b where b.daire = ? order by b.id desc")
                .setParameter(0, daire)
                .list();
        return (List<DaireBelge>) list;
    }

    public List<DaireBelge> getBelgeListByTalep(Talep talep){
        List list = getSessionFactory().getCurrentSession()
                .createQuery("from DaireBelge b where b.talep = ? order by b.id desc")
                .setParameter(0, talep)
                .list();
        return (List<DaireBelge>) list;
    }

    public void belgeSil(DaireBelge daireBelge){
        getSessionFactory().getCurrentSession().delete(daireBelge.getBelge());
        getSessionFactory().getCurrentSession().delete(daireBelge);
    }

    public List<DaireBelge> getBelgeListBySorguSonucu(int first, int pageSize, BelgeSorguKriteri sorguKriteri ,SessionInfo sessionInfo) {
        StringBuffer sb = null;
        DaireBelgeCriteria daireBelgeCriteria = new DaireBelgeCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession(), first, pageSize);
        List<DaireBelge> daireBelgeList = (List<DaireBelge>) daireBelgeCriteria.prepareResult();
        if(!sessionInfo.isAdminMi())
        sorguKriteri.setKullanici(null);
        BelgeCriteria belgeCriteria = new BelgeCriteria(sorguKriteri, sessionInfo ,getSessionFactory().getCurrentSession(), first, pageSize);
        for(Belge b : belgeCriteria.prepareResult()){
            daireBelgeList.add(new DaireBelge(b));
        }
        HashSet hs = new HashSet(daireBelgeList);
      return new ArrayList<DaireBelge>(hs);
    }

}
