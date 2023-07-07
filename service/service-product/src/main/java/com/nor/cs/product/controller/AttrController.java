package com.nor.cs.product.controller;


import com.nor.cs.common.result.Result;
import com.nor.cs.model.product.Attr;
import com.nor.cs.product.service.AttrService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/admin/product/attr")
@CrossOrigin
public class AttrController {
    @Resource
    private AttrService attrService;
    @GetMapping("list/{groupId}")
    public Result<List<Attr>> listAttrByGroupId(@PathVariable Long groupId){
        List<Attr> list = attrService.listByGroupId(groupId);
        return Result.successWithData(list);
    }
    
    @GetMapping("{id}")
    public Result<Attr> getAttrById(@PathVariable Long id){
        Attr attr = attrService.getById(id);
        return Result.successWithData(attr);
    }
    
    @PostMapping
    public Result saveAttr(@RequestBody Attr attr) {
        attrService.save(attr);
        return Result.successWithOutData();
    }
    
    @PutMapping
    public Result updateAttr(@RequestBody Attr attr) {
        attrService.updateById(attr);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result removeByAttrId(@PathVariable Long id) {
        attrService.removeById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("batchRemove")
    public Result batchRemoveAttr(@RequestBody List<Long> ids) {
        attrService.removeByIds(ids);
        return Result.successWithOutData();
    }
}

