package com.tuoguan.tg.vod.controller;


import com.tgxt.result.Result;
import com.tuoguan.tg.vod.entity.TCourses;
import com.tuoguan.tg.vod.entity.TUsers;
import com.tuoguan.tg.vod.entity.TVideo;
import com.tuoguan.tg.vod.service.TVideoService;
import com.tuoguan.tg.vod.utils.ConstantPropertiesUtil;
import com.tuoguan.tg.vod.utils.Signature;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 课程视频 前端控制器
 * 调用的时腾讯云的接口所以可以不使用mapper
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-13
 */
@RestController
@RequestMapping("/admin/vod/")
@CrossOrigin //解决跨域
public class TVideoController {
    @Resource
    private TVideoService tVideoService;

    //上传视频
    @PostMapping("uploadVideo")
    public Result<Map<String,Object>> uploadVideo(){
        Map<String,Object> videoMap = tVideoService.uploadVideo();
        return Result.ok(videoMap);
    }

    //添加
    @PostMapping("video/saveVideo")
    public Result<Object> saveVideo(@RequestBody TVideo tVideo) {
        boolean isSuccess = tVideoService.save(tVideo);
        if (isSuccess)
            return Result.ok(null).code(20000).message("添加成功！");
        else
            return Result.fail(null).code(20001).message("添加失败！");
    }

    //删除视频
    @DeleteMapping("remove/video/{fileId}")
    public Result<Object> removeVideo( @PathVariable String fileId) {
        tVideoService.removeVideo(fileId);
        return Result.ok(null);
    }

    //查询视频
    @GetMapping("video/getVideo/{courseId}")
    public Result<TVideo> getVideo(@PathVariable Long courseId){
        TVideo tVideo = tVideoService.getVideo(courseId);
        return Result.ok(tVideo);
    }
}

