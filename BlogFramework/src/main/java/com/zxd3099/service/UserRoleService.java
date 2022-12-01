package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.entity.UserRole;
import com.zxd3099.domain.vo.RoleVo;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2022-11-30 11:58:30
 */
public interface UserRoleService extends IService<UserRole> {
    List<RoleVo> selectRolesByUserId(Long userId);

    List<Long> getRoleIdsByUserId(Long userId);
}

