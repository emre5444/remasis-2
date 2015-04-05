/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.service;

import com.ronin.model.Sirket;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User {


    public CustomUserDetails(String username, String s, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, List<Sirket> sirketList) {
        super(username, s, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.sirketList = sirketList;
    }

    private List<Sirket> sirketList;

    public List<Sirket> getSirketList() {
        return sirketList;
    }

    public void setSirketList(List<Sirket> sirketList) {
        this.sirketList = sirketList;
    }
}