package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zxd3099
 * @create 2022-11-30-12:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSelectVo {
    private List<Long> roleIds;
    private List<RoleVo> roleVoList;
    private UserInfoVo userInfoVo;
}
