package com.prefabsoft.security.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.prefabsoft.security.model.PrefabAuthority;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import javax.validation.constraints.Past;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.context.annotation.Scope;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPrefabUsersByEmailAddressLike" })
@Component
@Scope("session")
public class PrefabUser implements UserDetails{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
    @Size(min = 6, max = 50)
    @Column(unique = true)
    private String emailAddress;

    @NotNull
    @Size(max = 64)
    private String password;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean accountNonExpired;

    @NotNull
    private Boolean accountNonLocked;

    @NotNull
    private Boolean credentialsNonExpired;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prefabUser", fetch = FetchType.EAGER)
    private Set<PrefabAuthority> authorities = new HashSet<PrefabAuthority>();

    private Boolean active;

    @NotNull
    @Value("#{new java.util.Date()}")
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateUpdate;
    
    @Override
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
    	Iterator<PrefabAuthority> it = authorities.iterator();
    	Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    	
        while (it.hasNext()) 
        {
        	grantedAuthorities.add(it.next());
        }
    	
    	return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return emailAddress;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
