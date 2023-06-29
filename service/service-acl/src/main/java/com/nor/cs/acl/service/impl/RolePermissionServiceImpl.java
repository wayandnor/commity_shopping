package com.nor.cs.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.acl.mapper.RolePermissionMapper;
import com.nor.cs.acl.service.api.PermissionService;
import com.nor.cs.acl.service.api.RolePermissionService;
import com.nor.cs.acl.service.api.RoleService;
import com.nor.cs.model.acl.Permission;
import com.nor.cs.model.acl.RolePermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionService {
    @Resource
    private PermissionService permissionService;

    @Override
    public List<Permission> getAllPermissionsByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper();
        wrapper.eq(RolePermission::getRoleId, roleId);
        List<RolePermission> roleSelectedPermissions = baseMapper.selectList(wrapper);
        List<Long> selectedPermissionIds = roleSelectedPermissions.stream().map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        List<Permission> allPermissions = permissionService.queryAllPermissions();
        this.setSelected(allPermissions, selectedPermissionIds);
        return allPermissions;
    }

    @Override
    public void updateRolePermission(Long roleId, Long[] permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId,roleId);
        baseMapper.delete(wrapper);
        List<RolePermission> rolePermissionToAdd = new ArrayList<>();
        for (Long permissionIdToAdd: permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionIdToAdd);
            rolePermissionToAdd.add(rolePermission);
        }
        this.saveBatch(rolePermissionToAdd);
    }

    private void setSelected(List<Permission> allPermissions, List<Long> selectedPermissionIds) {
        for (Permission permission : allPermissions) {
            if (selectedPermissionIds.contains(permission.getId())) {
                permission.setSelect(true);
            }
            setSelected(permission.getChildren(), selectedPermissionIds);
        }
    }
}
