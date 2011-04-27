package com.prefabsoft.security.acl.web;

import com.prefabsoft.security.acl.model.PrefabAclEntry;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "prefabaclentrys", formBackingObject = PrefabAclEntry.class)
@RequestMapping("/prefabaclentrys")
@Controller
public class PrefabAclEntryController {
}
