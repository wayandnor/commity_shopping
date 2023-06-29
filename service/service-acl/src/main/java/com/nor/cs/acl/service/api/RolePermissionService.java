package com.nor.cs.acl.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.acl.Permission;
import com.nor.cs.model.acl.RolePermission;

import java.util.List;
import java.util.Map;

public interface RolePermissionService extends IService<RolePermission> {
    List<Permission> getAllPermissionsByRoleId(Long roleId);

    void updateRolePermission(Long roleId, Long[] permissionId);
}
