package com.tuoguan.tg.vod.service;

import org.springframework.web.multipart.MultipartFile;

//腾讯云对象存储文件上传
public interface FileService {
    //文件上传
    String upload(MultipartFile file);
}
