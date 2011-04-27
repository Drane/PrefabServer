package com.prefabsoft.security.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPrefabAuthoritysByPrefabUser" })
public class PrefabAuthority implements GrantedAuthority{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
    private PrefabUser prefabUser;

    @NotNull
    @Size(max = 50)
    private String authority;

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
    
    public static Collection<GrantedAuthority> findGrantedAuthoritysByPrefabUser(PrefabUser prefabUser){

    	Collection<? extends GrantedAuthority> list = findPrefabAuthoritysByPrefabUser(prefabUser).getResultList();
    	
    	return (Collection<GrantedAuthority>) list;
    }

}
