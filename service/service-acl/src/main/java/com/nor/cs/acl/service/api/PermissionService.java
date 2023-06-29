package com.nor.cs.acl.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.acl.Permission;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/23 14:29
 */
public interface PermissionService extends IService<Permission> {
    List<Permission> queryAllPermissions();

    void removePermissionRecursively(Long permissionId);

}
