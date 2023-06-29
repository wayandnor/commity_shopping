package com.nor.cs.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.acl.mapper.PermissionMapper;
import com.nor.cs.acl.service.api.PermissionService;
import com.nor.cs.acl.util.PermissionUtil;
import com.nor.cs.model.acl.Permission;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/23 14:29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<Permission> queryAllPermissions() {
        List<Permission> allPermissions = baseMapper.selectList(null);
        List<Permission> permissionTree = PermissionUtil.constructPermissionTree(allPermissions);
        return permissionTree;
    }

    @Override
    public void removePermissionRecursively(Long permissionId) {
        List<Long> toDeleteIdList = new ArrayList<>();
        toDeleteIdList.add(permissionId);
        this.findAllChildren(permissionId,toDeleteIdList);
        baseMapper.deleteBatchIds(toDeleteIdList);
    }
    

    private void findAllChildren(Long permissionId, List<Long> toDeleteIdList) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid, permissionId);
        List<Permission> childrenList = baseMapper.selectList(wrapper);
        for (Permission permission: childrenList) {
            Long childPermissionId = permission.getId();
            toDeleteIdList.add(childPermissionId);
            findAllChildren(childPermissionId,toDeleteIdList);
        }
    }
    
    
}
