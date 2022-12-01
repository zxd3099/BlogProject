package com.zxd3099.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd3099
 * @create 2022-11-26-15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "TagListDto")
public class TagListDto {
    private String name;
    private String remark;
}
