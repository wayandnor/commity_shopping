package com.nor.cs.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.acl.service.api.AdminService;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.acl.Admin;
import com.nor.cs.model.vo.acl.AdminQueryVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户管理")
@RequestMapping("/admin/acl/user")
@RestController
@CrossOrigin
public class AdminController {
    @Resource
    private AdminService adminService;


    @GetMapping("{page_num}/{page_size}")
    public Result<IPage<Admin>> getAdminList(@PathVariable Long page_num,
                                             @PathVariable Long page_size,
                                             AdminQueryVo adminQueryVo) {
        Page<Admin> pageParam = new Page<>(page_num, page_size);
        IPage<Admin> adminPage = adminService.queryAdminPage(pageParam, adminQueryVo);
        return Result.successWithData(adminPage);
    }

    @GetMapping("/{id}")
    public Result<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.successWithData(admin);
    }

    @PostMapping
    public Result addAdmin(@RequestBody Admin admin) {
        boolean success = adminService.save(admin);
        if (success) {
            return Result.successWithOutData();
        } else {
            return Result.fail();
        }
    }

    @PutMapping
    public Result updateAdminById(@RequestBody Admin admin) {
        boolean success = adminService.updateById(admin);
        if (success) {
            return Result.successWithOutData();
        } else {
            return Result.fail();
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteAdminById(@PathVariable Long id) {
        boolean success = adminService.removeById(id);
        if (success) {
            return Result.successWithOutData();
        } else {
            return Result.fail();
        }
    }

    @DeleteMapping("/batchRemove")
    public Result batchDeleteAdmin(@RequestBody List<Long> idList) {
        boolean success = adminService.removeByIds(idList);
        if (success) {
            return Result.successWithOutData();
        } else {
            return Result.fail();
        }
    }
}
