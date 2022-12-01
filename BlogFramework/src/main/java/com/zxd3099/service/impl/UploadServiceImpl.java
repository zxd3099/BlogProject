package com.zxd3099.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.zxd3099.domain.ResponseResult;
import com.zxd3099.enums.AppHttpCodeEnum;
import com.zxd3099.handler.exception.SystemException;
import com.zxd3099.service.UploadService;
import com.zxd3099.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author zxd3099
 * @create 2022-11-21-19:42
 */
@Service("UploadService")
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 判断文件类型和文件大小
        String originalFileName = img.getOriginalFilename();
        // 对文件名进行判断
        if (!originalFileName.endsWith(".png") || !originalFileName.endsWith(".jpeg") ||
            !originalFileName.endsWith(".jpg") || !originalFileName.endsWith(".bmp"))
        {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        // 上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFileName);
        String url = uploadOSS(img, filePath);

        return ResponseResult.okResult(url);
    }

    private String uploadOSS(MultipartFile imgFile, String filePath) {
        // 构造ossClient
        OSS ossClient = null;
        // 判断文件是否上传成功
        String url = "文件上传没有成功";
        try(InputStream inputStream = imgFile.getInputStream()) {
            ossClient = new OSSClientBuilder().build(endPoint, accessKey, secretKey);

            ossClient.putObject(bucket, filePath, inputStream);

            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10 );
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, filePath, HttpMethod.GET);
            req.setExpiration(expiration);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            url = signedUrl.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    // public void test() {
    //     OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey, secretKey);
    //     Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10 );
    //     GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, "2.jpg", HttpMethod.GET);
    //     req.setExpiration(expiration);
    //     URL signedUrl = ossClient.generatePresignedUrl(req);
    //     System.out.println(signedUrl);
    // }
}
