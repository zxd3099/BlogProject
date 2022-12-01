package com.zxd3099.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.TagListDto;
import com.zxd3099.domain.entity.Tag;
import com.zxd3099.domain.vo.PageVo;
import com.zxd3099.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-11-23 23:02:11
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult getTag(Long id);

    List<TagVo> listAllTag();
}

