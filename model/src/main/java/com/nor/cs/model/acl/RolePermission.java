package com.nor.cs.model.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nor.cs.model.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("role_permission")
public class RolePermission extends BaseEntity {
    @ApiModelProperty(value = "roleId")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "permissionId")
    @TableField("permission_id")
    private Long permissionId;

    @JsonIgnore
    @TableField("is_deleted")
    private Integer isDeleted;
}
