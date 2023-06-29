package com.nor.cs.sys.controller;


import com.nor.cs.common.result.Result;
import com.nor.cs.model.sys.Region;
import com.nor.cs.sys.service.RegionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
@RestController
@RequestMapping("/admin/sys/region")
@CrossOrigin
public class RegionController {
    @Resource
    private RegionService regionService;
    
    @GetMapping("/byKeyword/{keyword}")
    public Result<List<Region>> findByKeyword(@PathVariable String keyword) {
        List<Region> regionList = regionService.getByKeyword(keyword);
        return Result.successWithData(regionList);
    }
}

