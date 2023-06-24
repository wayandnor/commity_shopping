package com.nor.cs.acl.controller;

import com.nor.cs.acl.service.api.PermissionService;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.acl.Permission;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/23 14:28
 */
@Api
@RestController
@RequestMapping("/admin/acl/permission")
@CrossOrigin
public class PermissionController {
    @Resource
    private PermissionService permissionService;
    @GetMapping
    public Result<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.queryAllPermissions();
        return Result.successWithData(permissions);
    }
    
    @DeleteMapping("/{permissionId}")
    public Result deletePermissionById(@PathVariable Long permissionId) {
        permissionService.removePermissionRecursively(permissionId);
        return Result.successWithOutData();
    }
}
