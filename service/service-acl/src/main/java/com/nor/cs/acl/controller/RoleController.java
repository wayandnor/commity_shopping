package com.nor.cs.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.acl.service.api.RoleService;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.acl.Role;
import com.nor.cs.model.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/acl/role")
@CrossOrigin
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("{page_num}/{page_size}")
    public Result getAdminPage(@PathVariable Long page_num,
                               @PathVariable Long page_size,
                               RoleQueryVo roleQueryVo) {
        Page<Role> rolePage = new Page<>(page_num, page_size);
        System.out.println(page_num);
        System.out.println(page_size);
        IPage<Role> page = roleService.queryRolePage(rolePage,roleQueryVo);
        return Result.successWithData(page);
    }
}
