package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zxd3099
 * @create 2022-11-30-9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeVo {
    private List<MenuVo> menuVo;
    private List<Long> checkedKeys;
}
