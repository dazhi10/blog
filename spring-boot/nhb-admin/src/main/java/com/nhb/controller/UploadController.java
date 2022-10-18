package com.nhb.controller;


import com.nhb.domain.ResponseResult;
import com.nhb.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 大只
 * @date 2022/10/1 21:59
 */
@Api(tags = "文件上传模块")
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation("上传文章缩略图")
    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestPart(value = "img") MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
