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
@RooEntity(versionField = "", table = "acl_object_identity")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "object_id_class", "object_id_identity" }) })
public class PrefabAclObjectIdentity {

    @NotNull
    @ManyToOne(targetEntity = PrefabAclClass.class)
    @JoinColumn
    private PrefabAclClass object_id_class;

    @NotNull
    private Long object_id_identity;

    @ManyToOne(targetEntity = PrefabAclObjectIdentity.class)
    @JoinColumn
    private PrefabAclObjectIdentity parent_object;

    @NotNull
    @ManyToOne(targetEntity = PrefabAclSid.class)
    @JoinColumn
    private PrefabAclSid owner_sid;
    
    @NotNull
    private boolean entries_inheriting;
	
}
