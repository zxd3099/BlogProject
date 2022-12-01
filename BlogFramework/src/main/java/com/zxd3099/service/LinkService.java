package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-11-18 22:08:13
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult listLinks(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult postLink(Link link);

    ResponseResult getLinkById(Long id);

    ResponseResult putLink(Link link);

    ResponseResult deleteLink(Long id);
}

