package com.nor.cs.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.acl.mapper.AdminRoleMapper;
import com.nor.cs.acl.service.api.AdminRoleService;
import com.nor.cs.acl.service.api.AdminService;
import com.nor.cs.model.acl.AdminRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/21 22:37
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper,AdminRole> implements AdminRoleService {
    @Override
    public List<Long> getAdminAssignedRoleIdList(Long adminId) {
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        List<AdminRole> adminRoles = baseMapper.selectList(wrapper);
        List<Long> roleIdList = adminRoles.stream().map(AdminRole::getRoleId).collect(Collectors.toList());
        return roleIdList;
    }
}
