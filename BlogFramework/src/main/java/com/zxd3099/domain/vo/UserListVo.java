package com.zxd3099.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zxd3099
 * @create 2022-11-30-10:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListVo {
    private Long id;
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    private Date createTime;
    //更新人
    private Long updateBy;
    //最后编辑时间
    private Date updateTime;
}
