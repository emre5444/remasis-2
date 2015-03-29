package com.ronin.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.dao.api.IFinansalIslemlerDao;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.DaireBorcKalem;
import com.ronin.model.kriter.AidatSorguKriteri;
import com.ronin.model.sorguSonucu.BorcAlacakViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fcabi on 12.07.2014.
 */
@Service(value = "finansalIslemlerService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class FinansalIslemlerService implements IFinansalIslemlerService {


    @Autowired
    IFinansalIslemlerDao iFinansalIslemlerDao;

    @Transactional(readOnly = false)
    public List<BorcAlacakViewBean> getBorcAlacakDurumuForDaire(SessionInfo sessionInfo, Daire daire, Date dateWitoutTime) {
        return iFinansalIslemlerDao.getBorcAlacakDurumuForDaire(sessionInfo, daire,dateWitoutTime);
    }

    @Transactional(readOnly = false)
    public List<BorcAlacakViewBean> getBorcAlacakDurumu(SessionInfo sessionInfo, Date dateWitoutTime) {
        return iFinansalIslemlerDao.getBorcAlacakDurumu(sessionInfo, dateWitoutTime);
    }

    @Transactional(readOnly = false)
    public List<DaireBorc> getListCriteriaForPaging(int first, int pageSize, AidatSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        return iFinansalIslemlerDao.getListCriteriaForPaging(first, pageSize, sorguKriteri, sessionInfo);
    }


    public IFinansalIslemlerDao getiFinansalIslemlerDao() {
        return iFinansalIslemlerDao;
    }

    public void setiFinansalIslemlerDao(IFinansalIslemlerDao iFinansalIslemlerDao) {
        this.iFinansalIslemlerDao = iFinansalIslemlerDao;
    }

    @Transactional(readOnly = false)
    public void addDaireBorc(DaireBorc daireBorc) {
        iFinansalIslemlerDao.addDaireBorc(daireBorc);
    }

    @Transactional(readOnly = false)
    public void addDaireBorcKalem(DaireBorcKalem daireBorcKalem) {
        iFinansalIslemlerDao.addDaireBorcKalem(daireBorcKalem);
    }

    @Transactional(readOnly = false)
    public void updateObject(Object object) {
        iFinansalIslemlerDao.updateObject(object);
    }

    @Transactional(readOnly = false)
    public void deleteObject(Object object) {
        iFinansalIslemlerDao.deleteObject(object);
    }

}
