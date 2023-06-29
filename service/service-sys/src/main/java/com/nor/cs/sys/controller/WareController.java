package com.nor.cs.sys.controller;


import com.nor.cs.common.result.Result;
import com.nor.cs.model.sys.Ware;
import com.nor.cs.sys.service.WareService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-26
 */
@RestController
@RequestMapping("/admin/sys/ware")
@CrossOrigin
public class WareController {
    @Resource
    private WareService wareService;
    @GetMapping("/{ware_id}")
    public Result<Ware> findWareById(@PathVariable Long ware_id) {
        Ware ware = wareService.getById(ware_id);
        return Result.successWithData(ware);
    }
    
    @GetMapping("/listAll")
    public Result<List<Ware>> listAllWare(){
        List<Ware> wareList = wareService.list();
        return Result.successWithData(wareList);
    }
}

