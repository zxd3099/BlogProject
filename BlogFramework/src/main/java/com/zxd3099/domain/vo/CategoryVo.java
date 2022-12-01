package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd3099
 * @create 2022-11-18-18:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    private Long id;
    private String categoryName;
    private String description;
    private String status;
}
