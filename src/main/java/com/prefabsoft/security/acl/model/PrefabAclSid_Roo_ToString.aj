// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.prefabsoft.security.acl.model;

import java.lang.String;

privileged aspect PrefabAclSid_Roo_ToString {
    
    public String PrefabAclSid.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Principal: ").append(getPrincipal()).append(", ");
        sb.append("Sid: ").append(getSid());
        return sb.toString();
    }
    
}
