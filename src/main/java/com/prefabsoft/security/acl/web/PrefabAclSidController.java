package com.prefabsoft.security.acl.web;

import com.prefabsoft.security.acl.model.PrefabAclSid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "prefabaclsids", formBackingObject = PrefabAclSid.class)
@RequestMapping("/prefabaclsids")
@Controller
public class PrefabAclSidController {
}
