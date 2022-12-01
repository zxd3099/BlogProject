package com.zxd3099.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd3099
 * @create 2022-11-28-20:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCategoryVo {
    @ExcelProperty("分类名")
    private String categoryName;

    @ExcelProperty("描述")
    private String categoryDescription;

    @ExcelProperty("状态0:正常;状态1:禁用")
    private String categoryStatus;
}
