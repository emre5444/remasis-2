package com.ronin.common.dao;

import java.util.List;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.model.Daire;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.SifreHatirlatma;
import com.ronin.model.constant.KullaniciTipi;
import com.ronin.model.kriter.KullaniciSorguKriteri;

public interface IKullaniciDAO {

	public void addKullanici(Kullanici user);

	public void updateKullanici(Kullanici user);

	public void deleteKullanici(Kullanici user);

	public Kullanici getKullaniciById(Long id);
        
    public Kullanici getKullaniciByUsername(String username);

    public Kullanici getKullaniciByEmail(String email);

    public long getBekleyenSifreIslemi(Long kullaniciId);

    List<Kullanici> getListCriteriaForPaging(int first, int pageSize, KullaniciSorguKriteri sorguKriteri , SessionInfo sessionInfo);

	public List<Kullanici> getKullaniciList();

    public List<KullaniciDaire> getDaireListByKullanici(Kullanici kullanici);

    void updateKullaniciDaire(List<KullaniciDaire> kullaniciDaireList , Kullanici kullanici);

    public List<KullaniciDaire> getKullaniciListByDaire(Daire daire);

     List<KullaniciDaire> getKullaniciListByDaire(Daire daire , KullaniciTipi kullaniciTipi);

    void addKullaniciSirket(KullaniciSirket kullaniciSirket);

    void sifreHatirlatmaIstekGonder(SifreHatirlatma sifreHatirlatma);
}
