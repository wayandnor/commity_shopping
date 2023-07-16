package com.nor.cs.acl.controller;

import com.nor.cs.acl.service.api.PermissionService;
import com.nor.cs.acl.service.api.RolePermissionService;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.acl.Permission;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/23 14:28
 */
@Api
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {
    @Resource
    private PermissionService permissionService;
    
    @Resource
    private RolePermissionService rolePermissionService;
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
    
    @PostMapping
    public Result savePermission(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.successWithOutData();
    }
    
    @PutMapping
    public Result updatePermission(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.successWithOutData();
    }
    
    @GetMapping("toAssign/{roleId}")
    public Result getRolePermission(@PathVariable Long roleId) {
        List<Permission> allPermissions = rolePermissionService.getAllPermissionsByRoleId(roleId);
        return Result.successWithData(allPermissions);
    }
    
    @PostMapping("doAssign")
    public Result updateRolePermission(@RequestParam Long roleId,
                                    @RequestParam Long[] permissionId) {
        rolePermissionService.updateRolePermission(roleId,permissionId);
        return Result.successWithOutData();
    }
}
