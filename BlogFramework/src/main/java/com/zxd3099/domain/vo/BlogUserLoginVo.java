package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd3099
 * @create 2022-11-19-10:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {
    private String token;
    private UserInfoVo userInfo;
}
