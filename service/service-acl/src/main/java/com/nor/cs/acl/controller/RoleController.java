package com.nor.cs.acl.controller;

import com.nor.cs.acl.service.api.RoleService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
@Api(tags = "角色管理")
@Controller
@RequestMapping("/admin/acl/role")
@CrossOrigin
public class RoleController {
    @Resource
    private RoleService service;
    
    
}
