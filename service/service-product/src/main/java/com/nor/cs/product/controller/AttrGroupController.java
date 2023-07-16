package com.nor.cs.product.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nor.cs.common.result.Result;
import com.nor.cs.model.product.AttrGroup;
import com.nor.cs.model.vo.product.AttrGroupQueryVo;
import com.nor.cs.product.service.api.AttrGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author north
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/admin/product/attrGroup")
//@CrossOrigin
public class AttrGroupController {
    @Resource
    private AttrGroupService attrGroupService;
    @GetMapping("{pageNum}/{pageSize}")
    public Result<IPage<AttrGroup>> searchAttrGroupPage(@PathVariable Integer pageNum,
                                                        @PathVariable Integer pageSize,
                                                        AttrGroupQueryVo attrGroupQueryVo) {
        Page<AttrGroup> pageParam = new Page<>(pageNum, pageSize);
        IPage<AttrGroup> page = attrGroupService.getPageByFilter(pageParam, attrGroupQueryVo);
        return Result.successWithData(page);
    }
    
    @GetMapping("{id}")
    public Result<AttrGroup> getAttrGroupById(@PathVariable Long id){
        AttrGroup attrGroup = attrGroupService.getById(id);
        return Result.successWithData(attrGroup);
    }
    
    @PostMapping
    public Result saveAttrGroup(@RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return Result.successWithOutData();
    }
    
    @PutMapping
    public Result updateAttrGroup(@RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("{id}")
    public Result removeById(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return Result.successWithOutData();
    }
    
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        attrGroupService.removeByIds(ids);
        return Result.successWithOutData();
    }
    
    @GetMapping("all")
    public Result<List<AttrGroup>> getAll() {
        List<AttrGroup > list = attrGroupService.listOrderById();
        return Result.successWithData(list);
    }
}

