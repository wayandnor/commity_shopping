package com.nor.cs.controller;

import com.nor.cs.common.auth.AuthContextHolder;
import com.nor.cs.common.result.Result;
import com.nor.cs.service.api.HomeService;
import com.nor.cs.service.api.ItemService;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/home")
public class ItemApiController {
    @Resource
    private ItemService itemService;
    
    @GetMapping("item/{item_id}")
    public Result productDetail(@PathVariable Long item_id) {
        Long userId = AuthContextHolder.getUserId();
        Map<String,Object> map = itemService.getProductDeatail(item_id,userId);
        return Result.successWithData(map);
    }
}
