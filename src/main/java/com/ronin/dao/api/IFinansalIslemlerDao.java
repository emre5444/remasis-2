/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ronin.dao.api;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.model.Daire;
import com.ronin.model.DaireBorc;
import com.ronin.model.DaireBorcKalem;
import com.ronin.model.kriter.AidatSorguKriteri;
import com.ronin.model.sorguSonucu.BorcAlacakViewBean;

import java.util.Date;
import java.util.List;

/**
 * @author esimsek
 */
public interface IFinansalIslemlerDao {
    List<BorcAlacakViewBean> getBorcAlacakDurumu(SessionInfo sessionInfo, Date dateWitoutTime);

    List<BorcAlacakViewBean> getBorcAlacakDurumuForDaire(SessionInfo sessionInfo, Daire daire, Date dateWitoutTime);

    List<DaireBorc> getListCriteriaForPaging(int first, int pageSize, AidatSorguKriteri sorguKriteri, SessionInfo sessionInfo);

    void addDaireBorc(DaireBorc daireBorc);

    void addDaireBorcKalem(DaireBorcKalem daireBorcKalem);
}
