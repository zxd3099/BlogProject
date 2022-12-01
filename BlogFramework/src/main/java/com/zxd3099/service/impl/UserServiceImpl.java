package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.UserDto;
import com.zxd3099.domain.entity.*;
import com.zxd3099.domain.vo.*;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.handler.exception.SystemException;
import com.zxd3099.mapper.UserMapper;
import com.zxd3099.service.UserRoleService;
import com.zxd3099.service.UserService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-11-18 22:43:01
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public ResponseResult userInfo() {
        // 获取当前用户Id
        long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = getById(userId);
        // 封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        if (user.getId() != SecurityUtils.getUserId()) {
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        if (StringUtils.hasText(user.getNickName()) && isExist(User::getNickName, user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (StringUtils.hasText(user.getEmail()) && isExist(User::getEmail, user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (StringUtils.hasText(user.getPhonenumber()) && isExist(User::getPhonenumber, user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getUserPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        // 对数据进行存在性判断
        if (isExist(User::getUserName, user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (isExist(User::getNickName, user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (StringUtils.hasText(user.getEmail()) && isExist(User::getEmail, user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (StringUtils.hasText(user.getPhonenumber()) && isExist(User::getPhonenumber, user.getPhonenumber())) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        // 对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodePassword);
        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean isExist(SFunction<User, ?> column, Object value) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, value);
        return count(queryWrapper) > 0;
    }

    @Override
    public ResponseResult selectUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String userStatus) {
        // 分页查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        queryWrapper.eq(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber);
        queryWrapper.eq(StringUtils.hasText(userStatus), User::getUserStatus, userStatus);

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        // 封装数据返回
        List<User> records = page.getRecords();
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(records, UserListVo.class);

        return ResponseResult.okResult(userListVos);
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        if (SecurityUtils.getUserId() == id) {
            return ResponseResult.errorResult();
        }
        update(Wrappers.<User>update()
                .lambda()
                .eq(User::getId, id)
                .set(User::getUpdateBy, SecurityUtils.getUserId())
                .set(User::getUpdateTime, new Date())
                .set(User::getDelFlag, 1));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addUser(UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        save(user);

        List<UserRole> userRoleList = userDto.getRoleIds().stream()
                .map(p -> new UserRole(user.getId(), p))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        User user = getById(id);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        List<RoleVo> roleVos = userRoleService.selectRolesByUserId(id);
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(id);

        UserSelectVo userSelectVo = new UserSelectVo(roleIds, roleVos, userInfoVo);
        return ResponseResult.okResult(userSelectVo);
    }

    @Override
    public ResponseResult updateUser(UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        save(user);

        List<UserRole> userRoleList = userDto.getRoleIds().stream()
                .map(p -> new UserRole(user.getId(), p))
                .collect(Collectors.toList());
        userRoleService.saveOrUpdateBatch(userRoleList);
        return ResponseResult.okResult();
    }
}