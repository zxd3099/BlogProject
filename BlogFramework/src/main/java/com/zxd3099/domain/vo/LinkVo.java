package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd3099
 * @create 2022-11-18-22:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    private Long id;
    //网站名
    private String websiteName;
    //logo
    private String logo;
    //描述
    private String websiteDescription;
    //网站地址
    private String address;
}
