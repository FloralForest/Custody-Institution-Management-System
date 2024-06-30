package com.tuoguan.tg.vod.controller;

import com.tgxt.result.Result;
import com.tuoguan.tg.vod.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

//腾讯云对象存储文件上传
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file/")
@CrossOrigin//解决跨域
public class FileUploadController {

    @Resource
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public Result<String> uploadFile(MultipartFile file){
        String upload = fileService.upload(file);//调用service完成上传
        return Result.ok(upload).message("文件上传成功");
    }
}
