package com.nor.cs.model.vo.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/6/19 11:07
 */
@Data
@ApiModel(description = "角色查询实体")
public class RoleQueryVo {
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
