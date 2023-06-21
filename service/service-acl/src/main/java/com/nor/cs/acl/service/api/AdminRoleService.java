package com.nor.cs.acl.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nor.cs.model.acl.AdminRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/21 22:35
 */

public interface AdminRoleService extends IService<AdminRole> {

    List<Long> getAdminAssignedRoleIdList(Long adminId);
}
