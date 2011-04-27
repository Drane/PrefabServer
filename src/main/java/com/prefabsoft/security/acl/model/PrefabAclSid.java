package com.prefabsoft.security.acl.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@RooJavaBean
@RooToString
@RooEntity(versionField = "", table = "acl_sid")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"sid", "principal" }) })
public class PrefabAclSid {

    @NotNull
    private Boolean principal;

    @NotNull
    private String sid;
}
