package com.zxd3099.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色信息表(Role)表实体类
 *
 * @author makejava
 * @since 2022-11-26 10:14:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role  {
    @TableId
    private Long id;

    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 1代表删除）
    private String delFlag;
    //创建人的用户id
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //发布时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //最后编辑时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //备注
    private String remark;

}

