package com.nor.cs.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.acl.service.api.RoleService;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.acl.Role;
import com.nor.cs.model.vo.acl.RoleQueryVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/acl/role")
//@CrossOrigin
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/{page_num}/{page_size}")
    public Result<IPage<Role>> getRolePage(@PathVariable Long page_num,
                               @PathVariable Long page_size,
                               RoleQueryVo roleQueryVo) {
        Page<Role> rolePage = new Page<>(page_num, page_size);
        System.out.println(page_num);
        System.out.println(page_size);
        IPage<Role> page = roleService.queryRolePage(rolePage,roleQueryVo);
        return Result.successWithData(page);
    }
    
    @GetMapping("/{id}")
    public Result<Role> getRoleById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.successWithData(role);
    }
    
    @PutMapping
    public Result updateRoleById(@RequestBody Role role){
        boolean isSuccess = roleService.updateById(role);
        if (isSuccess) {
            return Result.successWithOutData();
        }else {
            return Result.fail();
        }
    }
    
    @PostMapping
    public Result createRole(@RequestBody Role role) {
        boolean success = roleService.save(role);
        if (success) {
            return Result.successWithOutData();
        }else {
            return Result.fail();
        }
    }
    
    @DeleteMapping("/{id}")
    public Result deleteRoleById(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("/batchRemove")
    public Result batchDeleteRole(@RequestBody List<Long> ids) {
        roleService.removeByIds(ids);
        return Result.successWithOutData();
    }
}
