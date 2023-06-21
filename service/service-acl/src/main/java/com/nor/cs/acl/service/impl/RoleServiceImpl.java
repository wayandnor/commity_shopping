package com.nor.cs.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.acl.mapper.RoleMapper;
import com.nor.cs.acl.service.api.AdminRoleService;
import com.nor.cs.acl.service.api.RoleService;
import com.nor.cs.model.acl.AdminRole;
import com.nor.cs.model.acl.Role;
import com.nor.cs.model.vo.acl.RoleQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private AdminRoleService adminRoleService;

    @Override
    public IPage<Role> queryRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (roleQueryVo.getRoleName() != null) {
            wrapper.like(Role::getRoleName, roleQueryVo.getRoleName());
        }
        IPage<Role> rolePage = baseMapper.selectPage(pageParam, wrapper);
        return rolePage;
    }

    @Override
    public Map<String, List> getAdminRolesById(Long adminId) {
        List<Role> allRoleList = baseMapper.selectList(null);
        List<Long> assignedRoleIdList = adminRoleService.getAdminAssignedRoleIdList(adminId);
        List<Role> assignedRoleList = new ArrayList<>();
        for (Role role : allRoleList) {
            if (assignedRoleIdList.contains(role.getId())) {
                assignedRoleList.add(role);
            }
        }
        Map<String, List> resultMap = new HashMap<>();
        resultMap.put("allRolesList", allRoleList);
        //用户分配角色列表
        resultMap.put("assignRoles", assignedRoleList);
        return resultMap;
    }

    @Override
    @Transactional
    public void assignRoleToAdminById(Long adminId, Long[] roleIds) {
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId, adminId);
        adminRoleService.remove(wrapper);
        List<AdminRole> toAddList = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            toAddList.add(adminRole);
        }
        adminRoleService.saveBatch(toAddList);
    }
}
