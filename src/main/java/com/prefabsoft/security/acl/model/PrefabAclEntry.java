package com.prefabsoft.security.acl.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(versionField = "", table = "acl_entry")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"acl_object_identity", "ace_order" }) })
public class PrefabAclEntry {
	
    @NotNull
    @ManyToOne(targetEntity = PrefabAclObjectIdentity.class)
    @JoinColumn
    private PrefabAclObjectIdentity acl_object_identity;

    @NotNull
    private Integer ace_order;

    @NotNull
    @ManyToOne(targetEntity = PrefabAclSid.class)
    @JoinColumn
    private PrefabAclSid sid;
    
    @NotNull
    private Integer mask;
    
    @NotNull
    private boolean granting;
    
    @NotNull
    private boolean audit_success;
    
    @NotNull
    private boolean audit_failure;
	
}
