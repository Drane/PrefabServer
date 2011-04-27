package com.prefabsoft.security.acl.web;

import com.prefabsoft.security.acl.model.PrefabAclObjectIdentity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "prefabaclobjectidentitys", formBackingObject = PrefabAclObjectIdentity.class)
@RequestMapping("/prefabaclobjectidentitys")
@Controller
public class PrefabAclObjectIdentityController {
}
