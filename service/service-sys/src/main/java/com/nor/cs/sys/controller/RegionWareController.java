package com.nor.cs.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.sys.Region;
import com.nor.cs.model.sys.RegionWare;
import com.nor.cs.model.vo.sys.RegionWareQueryVo;
import com.nor.cs.sys.service.RegionWareService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
@RestController
@RequestMapping("/admin/sys/regionWare")
@CrossOrigin
public class RegionWareController {
    
    @Resource
    private RegionWareService regionWareService;
    
    @GetMapping("{page_num}/{page_size}")
    public Result<IPage<RegionWare>> getRegionWarePage(@PathVariable Long page_num,
                                                       @PathVariable Long page_size,
                                                       RegionWareQueryVo regionWareQueryVo) {
        IPage<RegionWare> pageParam = new Page<>(page_num,page_size);
        IPage<RegionWare> regionWarePage = regionWareService.pageByFilter(pageParam,regionWareQueryVo);
        return Result.successWithData(regionWarePage);
    }
    
    @PostMapping
    public Result saveRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.successWithOutData();
    }
    
    @PutMapping("{id}/{status}")
    public Result updateRegionWareStatus(@PathVariable Long id,
                                         @PathVariable Integer status){
        regionWareService.updateStatusById(id,status);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result removeById(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.successWithOutData();
    }

}

