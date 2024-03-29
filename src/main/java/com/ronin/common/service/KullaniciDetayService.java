/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.service;

import com.ronin.common.model.Kullanici;
import com.ronin.common.model.KullaniciRol;
import com.ronin.common.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testng.log4testng.Logger;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * @author esimsek
 */
@Service("kullaniciDetayService")
@Transactional(readOnly = false)
public class KullaniciDetayService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(KullaniciDetayService.class);

    @Autowired
    private IKullaniciService kullaniciService;

    @Autowired
    private IOrtakService ortakService;

    @Autowired
    private IRolService rolService;

    @Autowired
    @Qualifier("labels")
    private MessageSource labels;

    @Autowired
    @Qualifier("messages")
    private MessageSource messages;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Locale locale = null;
        try {
            locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        } catch (NullPointerException e) {
            locale = new Locale("tr", "TR");
        }
        try {
            String mesaj = messages.getMessage("gecersiz_kullanici_adi", null, locale);
            if (username == null || username.trim().isEmpty()) {
                throw new UsernameNotFoundException(mesaj);
            }
            Kullanici kullanici = kullaniciService.getKullaniciByUsername(username);
            if (kullanici == null) {
                throw new UsernameNotFoundException(mesaj);
            }

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new CustomUserDetails(kullanici.getUsername(),
                    kullanici.getPassword().toLowerCase(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(kullanici), kullanici.getSirketList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Kullanici kullanici) {
        Set<GrantedAuthority> authList = getGrantedAuthorities(rolService.getRolListByKullanici(kullanici));
        return authList;
    }

    public static Set<GrantedAuthority> getGrantedAuthorities(List<Rol> rolList) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol : rolList) {
            authorities.add(new SimpleGrantedAuthority(rol.getAd()));
        }
        return new HashSet<GrantedAuthority>(authorities);
    }

}
