package com.nhb.service;

import com.nhb.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 上传文章缩略图
     */
    ResponseResult uploadImg(MultipartFile img);
}
