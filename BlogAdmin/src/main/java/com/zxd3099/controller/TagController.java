package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.dto.TagListDto;
import com.zxd3099.domain.entity.Tag;
import com.zxd3099.domain.vo.PageVo;
import com.zxd3099.domain.vo.TagVo;
import com.zxd3099.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zxd3099
 * @create 2022-11-23-23:11
 * 为了方便后期对文章进行管理，需要提供标签的功能，一个文章可以有多个标签。
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @SystemLog(Description = "分页查询标签")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping("/addTag")
    @SystemLog(Description = "添加标签")
    public ResponseResult addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    @SystemLog(Description = "删除标签")
    public ResponseResult deleteTag(@PathVariable("id") Long id) {
        return tagService.deleteTag(id);
    }

    @PutMapping
    @SystemLog(Description = "修改标签")
    public ResponseResult updateTag(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    @GetMapping("/{id}")
    @SystemLog(Description = "获取指定id的标签数据")
    public ResponseResult getTag(@PathVariable("id") Long id) {
        return tagService.getTag(id);
    }

    @GetMapping("/listAllTag")
    @SystemLog(Description = "查询所有标签")
    public ResponseResult listAllTag() {
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}
