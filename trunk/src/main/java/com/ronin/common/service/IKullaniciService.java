package com.ronin.common.service;
/**
 *
 * @author msevim
 */
import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.common.model.Rol;
import com.ronin.model.Daire;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.SifreHatirlatma;
import com.ronin.model.kriter.KullaniciSorguKriteri;

import java.util.List;


public interface IKullaniciService {
	
	public void addKullanici(Kullanici user);
	
	public void updateKullanici(Kullanici user);

    public void updateKullaniciWithPasword(Kullanici user);

	public void deleteKullanici(Kullanici user);
	
	public Kullanici getKullaniciById(Long id);
        
    public Kullanici getKullaniciByUsername(String username);

    public Kullanici getKullaniciByEmail(String email);

    public long getBekleyenSifreIslemi(Long kullaniciId);
	
	public List<Kullanici> getKullaniciList();

    public List<Kullanici> getListCriteriaForPaging(int first, int pageSize, KullaniciSorguKriteri sorguKriteri , SessionInfo sessionInfo);

    public List<KullaniciDaire> getDaireListByKullanici(Kullanici kullanici);

    public List<KullaniciDaire> addTempDaireToKullanici(List<KullaniciDaire> kullaniciDaireList , Kullanici kullanici , Daire daire);

    public List<KullaniciDaire> deleteTempDaireToKullanici(List<KullaniciDaire> kullaniciDaireList , KullaniciDaire kullaniciDaire);

    void updateKullaniciDaire(List<KullaniciDaire> kullaniciDaireList , Kullanici kullanici);

    public void addKullaniciSirket(KullaniciSirket kullaniciSirket);

    public void sifreHatirlatmaIstekGonder(SifreHatirlatma sifreHatirlatma);

    public int getActiveUsersCount(SessionInfo sessionInfo);
}
