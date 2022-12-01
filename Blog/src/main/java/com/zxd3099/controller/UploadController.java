package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zxd3099
 * @create 2022-11-21-19:39
 */
@RestController
@Api(tags = "图片上传", description = "图片上传相关接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(Description = "上传图片文件")
    @ApiOperation(value = "上传图片文件", notes = "图片格式为image")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img",value = "图片文件")
    })
    public ResponseResult uploadImg(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
