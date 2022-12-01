package com.zxd3099.controller;

import com.zxd3099.annotation.SystemLog;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zxd3099
 * @create 2022-11-28-19:38
 */
@RestController
public class UploaderController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(Description = "上传文件类型必须为.png/.jpg/.jpeg/.bmp")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        return uploadService.uploadImg(multipartFile);
    }
}
