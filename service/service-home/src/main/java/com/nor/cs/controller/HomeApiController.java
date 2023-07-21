package com.nor.cs.controller;

import com.nor.cs.common.auth.AuthContextHolder;
import com.nor.cs.common.result.Result;
import com.nor.cs.service.api.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/home")
public class HomeApiController {
    @Resource
    private HomeService homeService;
    
    @GetMapping("index")
    public Result getIndexData(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        Map<String, Object> data = homeService.indexPageData(userId);
        return Result.successWithData(data);
    }
}
