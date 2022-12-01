package com.zxd3099.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2022-11-18 16:22:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
@ApiModel(description = "分类实体")
public class Category  {
    @TableId
    private Long id;

    //分类名
    private String categoryName;
    //父分类id，如果没有父分类为-1
    private Long pid;
    //描述
    private String categoryDescription;
    //状态0:正常,1禁用
    private String categoryStatus;
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
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}