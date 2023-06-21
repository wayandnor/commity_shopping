package com.nor.cs.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nor.cs.acl.mapper.AdminMapper;
import com.nor.cs.acl.service.api.AdminService;
import com.nor.cs.model.acl.Admin;
import com.nor.cs.model.vo.acl.AdminQueryVo;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public IPage<Admin> queryAdminPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String userName = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(userName)) {
            lambdaQueryWrapper.eq(Admin::getUsername, userName);
        }
        if (!StringUtil.isEmpty(name)) {
            lambdaQueryWrapper.like(Admin::getName, name);
        }
        return baseMapper.selectPage(pageParam,lambdaQueryWrapper);
    }
}
