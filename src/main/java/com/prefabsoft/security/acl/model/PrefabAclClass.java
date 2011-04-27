package com.prefabsoft.security.acl.model;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooEntity(versionField = "", table = "acl_class")
public class PrefabAclClass {

    @NotNull
    @Column(name = "class", unique = true)
    private String clazz;
}
