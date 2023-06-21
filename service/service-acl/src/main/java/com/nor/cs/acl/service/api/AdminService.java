package com.nor.cs.acl.service.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.acl.Admin;
import com.nor.cs.model.vo.acl.AdminQueryVo;

public interface AdminService extends IService<Admin> {
    IPage<Admin> queryAdminPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo);
}
