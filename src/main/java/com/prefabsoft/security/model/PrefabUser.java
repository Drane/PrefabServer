package com.prefabsoft.security.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.prefabsoft.model.PrefabResource;
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
import com.prefabsoft.spring.validator.constraints.FieldMatch;

@FieldMatch.List({ @FieldMatch(first = "emailAddress", second = "confirmEmailAddress", message = "The email fields must match"), @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match") })
@RooJavaBean
@RooToString
@RooEntity(finders = { "findPrefabUsersByEmailAddressLike" })
@Component
@Scope("session")
public class PrefabUser implements UserDetails {
	
	private static final Log logger = LogFactory.getLog(PrefabUser.class);
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 6, max = 50)
    @Column(unique = true)
    private String userName;
    
    @NotNull
    @Size(min = 6, max = 50)
    @Column(unique = true)
    private String emailAddress;

    @Transient
    private String confirmEmailAddress;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    @Transient
    private String confirmPassword;

    @NotNull
    private Boolean enabled = true;

    @NotNull
    private Boolean accountNonExpired = true;

    @NotNull
    private Boolean accountNonLocked = true;

    @NotNull
    private Boolean credentialsNonExpired = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prefabUser", fetch = FetchType.EAGER)
    private Set<PrefabAuthority> authorities = new HashSet<PrefabAuthority>();

    private Boolean active = false;

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
        while (it.hasNext()) {
            grantedAuthorities.add(it.next());
        }
        return grantedAuthorities;
    }

	public String register(){
		logger.warn(this.toString());
		persist();
		return "result";
	}
    
    @Override
    public String getUsername() {
        return userName;
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
