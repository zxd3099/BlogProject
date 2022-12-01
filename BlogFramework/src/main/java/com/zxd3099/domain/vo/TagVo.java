package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd3099
 * @create 2022-11-26-17:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {
    private Long id;
    private String name;
    private String remark;
}
