package com.prefabsoft.security.acl.web;

import com.prefabsoft.security.acl.model.PrefabAclClass;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "prefabaclclasses", formBackingObject = PrefabAclClass.class)
@RequestMapping("/prefabaclclasses")
@Controller
public class PrefabAclClassController {
}
