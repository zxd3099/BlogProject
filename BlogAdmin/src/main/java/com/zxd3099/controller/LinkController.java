package com.zxd3099.controller;

import com.zxd3099.domain.ResponseResult;
import com.zxd3099.domain.entity.Link;
import com.zxd3099.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxd3099
 * @create 2022-11-30-17:59
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult listLinks(Integer pageNum, Integer pageSize, String name, String status) {
        return linkService.listLinks(pageNum, pageSize, name, status);
    }

    @PostMapping
    public ResponseResult postLink(@RequestBody Link link) {
        return linkService.postLink(link);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id) {
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult putLink(@RequestBody Link link) {
        return linkService.putLink(link);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id) {
        return linkService.deleteLink(id);
    }
}
