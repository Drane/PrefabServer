package com.prefabsoft.security.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prefabsoft.security.model.PrefabUser;

public class PrefabUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
    	
    	UserDetails ud = PrefabUser.findPrefabUsersByEmailAddressLike(username).getResultList().get(0); 
        return ud;
    }
}