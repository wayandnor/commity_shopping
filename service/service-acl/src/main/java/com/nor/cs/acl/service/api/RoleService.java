package com.nor.cs.acl.service.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.acl.Role;
import com.nor.cs.model.vo.acl.RoleQueryVo;

public interface RoleService extends IService<Role> {
    IPage<Role> queryRolePage(Page<Role> rolePage, RoleQueryVo roleQueryVo);
}
