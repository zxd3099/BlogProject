package com.zxd3099.service;

import com.zxd3099.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zxd3099
 * @create 2022-11-21-19:42
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
