package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxd3099
 * @create 2022-11-18-22:03
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链", description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(Description = "获取所有友链")
    @ApiOperation(value = "获取所有友链", notes = "获取所有友链")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
