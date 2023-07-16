package com.nor.cs.acl.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.nor.cs.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Api
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {
    @PostMapping("/login")
    public Result<Map<String, String>> login() {
        Map<String, String> data = new HashMap<>();
        data.put("token", "token-admin");
        return Result.successWithData(data);
    }

    @GetMapping("/info")
    public Result<Map<String, String>> getInfo() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "nor");
        data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.successWithData(data);
    }

    @PostMapping("logout")
    public Result logout() {
        return Result.successWithOutData();
    }
}
