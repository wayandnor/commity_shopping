package com.nor.cs.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nor.cs.model.acl.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
