package com.ronin.common.service;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.dao.IKullaniciDAO;
import com.ronin.common.dao.IRolDao;
import com.ronin.common.model.Kullanici;
import com.ronin.model.Daire;
import com.ronin.model.KullaniciDaire;
import com.ronin.model.KullaniciSirket;
import com.ronin.model.SifreHatirlatma;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.KullaniciTipi;
import com.ronin.model.enums.Sorun;
import com.ronin.model.kriter.KullaniciSorguKriteri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service("kullaniciService")
@Transactional(readOnly = false)
public class KullaniciService implements IKullaniciService {

    @Autowired
    IKullaniciDAO kullaniciDAO;

    @Autowired
    IRolDao rolDao;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    @Transactional(readOnly = false)
    public void addKullanici(Kullanici user) {

        String passwordToHash = user.getPassword();
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            user.setPassword(generatedPassword);
            getKullaniciDAO().addKullanici(user);
            //  getRolDao().relateUserWithRole(user);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void deleteKullanici(Kullanici user) {
        getKullaniciDAO().deleteKullanici(user);
    }

    @Transactional(readOnly = false)
    public void updateKullanici(Kullanici user) {
        getKullaniciDAO().updateKullanici(user);
    }

    @Transactional(readOnly = false)
    public void updateKullaniciWithPasword(Kullanici user) {

        String passwordToHash = user.getPassword();
        String generatedPassword = null;
        try {
            if (!StringUtils.isEmpty(passwordToHash)) {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                //Add password bytes to digest
                md.update(passwordToHash.getBytes());
                //Get the hash's bytes
                byte[] bytes = md.digest();
                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format
                generatedPassword = sb.toString();
                user.setPassword(generatedPassword);
            }
            getKullaniciDAO().updateKullanici(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public Kullanici getKullaniciById(Long id) {
        return getKullaniciDAO().getKullaniciById(id);
    }

    @Transactional(readOnly = false)
    public Kullanici getKullaniciByUsername(String username) {
        return getKullaniciDAO().getKullaniciByUsername(username);
    }

    @Transactional(readOnly = false)
    public Kullanici getKullaniciByEmail(String email) {
        return getKullaniciDAO().getKullaniciByEmail(email);
    }

    @Transactional(readOnly = false)
    public long getBekleyenSifreIslemi(Long kullaniciId) {
        return getKullaniciDAO().getBekleyenSifreIslemi(kullaniciId);
    }

    @Transactional(readOnly = false)
    public List<Kullanici> getKullaniciList() {
        return getKullaniciDAO().getKullaniciList();
    }

    public IKullaniciDAO getKullaniciDAO() {
        return kullaniciDAO;
    }

    public void setKullaniciDAO(IKullaniciDAO kullaniciDAO) {
        this.kullaniciDAO = kullaniciDAO;
    }

    @Transactional(readOnly = false)
    public List<Kullanici> getListCriteriaForPaging(int first, int pageSize, KullaniciSorguKriteri sorguKriteri, SessionInfo sessionInfo) {
        return kullaniciDAO.getListCriteriaForPaging(first, pageSize, sorguKriteri, sessionInfo);
    }

    @Transactional(readOnly = false)
    public List<KullaniciDaire> getDaireListByKullanici(Kullanici kullanici) {
        return kullaniciDAO.getDaireListByKullanici(kullanici);
    }

    public List<KullaniciDaire> addTempDaireToKullanici(List<KullaniciDaire> kullaniciDaireList, Kullanici kullanici, Daire daire) {
        if (kullaniciDaireList == null)
            kullaniciDaireList = new ArrayList<>();
        KullaniciDaire kullaniciDaire = new KullaniciDaire();
        kullaniciDaire.setDaire(daire);
        kullaniciDaire.setKullanici(kullanici);
        kullaniciDaire.setKullaniciTipi(KullaniciTipi.getSakinObject());
        kullaniciDaire.setDurum(Durum.getAktifObject());
        kullaniciDaireList.add(kullaniciDaire);
        return kullaniciDaireList;
    }

    public List<KullaniciDaire> deleteTempDaireToKullanici(List<KullaniciDaire> kullaniciDaireList, KullaniciDaire kullaniciDaire) {
        kullaniciDaireList.remove(kullaniciDaire);
        return kullaniciDaireList;
    }

    @Transactional(readOnly = false)
    public void updateKullaniciDaire(List<KullaniciDaire> kullaniciDaireList, Kullanici kullanici) {
        kullaniciDAO.updateKullaniciDaire(kullaniciDaireList, kullanici);
    }

    @Transactional(readOnly = false)
    public void addKullaniciSirket(KullaniciSirket kullaniciSirket) {
        kullaniciDAO.addKullaniciSirket(kullaniciSirket);
    }

    @Transactional(readOnly = false)
    public void sifreHatirlatmaIstekGonder(SifreHatirlatma sifreHatirlatma) {
        kullaniciDAO.sifreHatirlatmaIstekGonder(sifreHatirlatma);
    }

    @Transactional(readOnly = false)
    public Sorun kullaniciDaireIliskilendirmeKontrol(List<KullaniciDaire> kullaniciDaireList) {
        //birden fazla malik yada sakin varm? die bak?l?r
        for (KullaniciDaire kullaniciDaire : kullaniciDaireList) {
              List<KullaniciDaire> tempKdList = kullaniciDAO.getKullaniciListByDaire(kullaniciDaire.getDaire() , kullaniciDaire.getKullaniciTipi());
              if(tempKdList.size() > 0){
                  if(kullaniciDaire.getKullaniciTipi().isEvsahibiMi()){
                      return Sorun.MALIZ_ZATEN_VAR;
                  } else {
                      return Sorun.SAKIN_ZATEN_VAR;
                  }
              }
        }
        return null;
    }


    public int getActiveUsersCount(SessionInfo sessionInfo) {
        List<Object> principals = sessionRegistry.getAllPrincipals();

        List<String> usersNamesList = new ArrayList<String>();

        for (Object principal : principals) {
            if (principal instanceof CustomUserDetails) {
                if (!((CustomUserDetails) principal).getSirketList().isEmpty() &&
                        ((CustomUserDetails) principal).getSirketList().contains(sessionInfo.getSirket()))
                    usersNamesList.add(((CustomUserDetails) principal).getUsername());
            }
        }

        return usersNamesList.size();
    }

    public IRolDao getRolDao() {
        return rolDao;
    }

    public void setRolDao(IRolDao rolDao) {
        this.rolDao = rolDao;
    }

    public SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
}
