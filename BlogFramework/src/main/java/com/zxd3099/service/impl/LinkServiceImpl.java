package com.zxd3099.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxd3099.constants.SystemConstants;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Category;
import com.zxd3099.domain.entity.Link;
import com.zxd3099.domain.vo.LinkVo;
import com.zxd3099.mapper.LinkMapper;
import com.zxd3099.service.LinkService;
import com.zxd3099.utils.BeanCopyUtils;
import com.zxd3099.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-11-18 22:08:13
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Link::getLinkStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        // 封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult listLinks(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(name), Link::getWebsiteName, name);
        queryWrapper.eq(StringUtils.hasText(status), Link::getLinkStatus, status);
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult postLink(Link link) {
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult putLink(@RequestBody Link link) {
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        update(Wrappers.<Link>update()
                .lambda()
                .eq(Link::getId, id)
                .set(Link::getUpdateBy, SecurityUtils.getUserId())
                .set(Link::getUpdateTime, new Date())
                .set(Link::getDelFlag, 1));
        return ResponseResult.okResult();
    }
}

