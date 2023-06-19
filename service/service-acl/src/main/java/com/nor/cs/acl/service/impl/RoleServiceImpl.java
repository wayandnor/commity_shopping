package com.nor.cs.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.acl.mapper.RoleMapper;
import com.nor.cs.acl.service.api.RoleService;
import com.nor.cs.model.acl.Role;
import com.nor.cs.model.vo.acl.RoleQueryVo;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public IPage<Role> queryRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (roleQueryVo.getRoleName() != null){
            wrapper.like(Role::getRoleName, roleQueryVo.getRoleName());
        }
        IPage<Role> rolePage = baseMapper.selectPage(pageParam, wrapper);
        return rolePage;
    }
}
