package com.nor.cs.acl.util;

import com.nor.cs.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/23 14:43
 */
public class PermissionUtil {
    public static List<Permission> constructPermissionTree(List<Permission> allPermissions) {
        List<Permission> permissionTree = new ArrayList<>();
        for (Permission permission : allPermissions) {
            if (permission.getPid() == 0) {
                permissionTree.add(permission);
            }
        }

        for (Permission permission : permissionTree) {
            findPermissionChildren(permission, allPermissions);
        }
        return  permissionTree;
    }

    private static void findPermissionChildren(Permission parentPermission, List<Permission> allPermissions) {
        Long parentId = parentPermission.getId();
        List<Permission> children = parentPermission.getChildren();
        if (children == null) {
            children = new ArrayList<>();
            parentPermission.setChildren(children);
        }

        for (Permission permission : allPermissions) {
            if (parentId == permission.getPid()) {
                children.add(permission);
                findPermissionChildren(permission, allPermissions);
            }
        }
    }
}
