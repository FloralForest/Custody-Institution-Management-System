package com.tuoguan.tg.vod.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import com.tuoguan.tg.vod.entity.TVideo;
import com.tuoguan.tg.vod.mapper.TVideoMapper;
import com.tuoguan.tg.vod.service.TVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuoguan.tg.vod.utils.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tuoguan
 * @since 2024-04-13
 */
@Service
@Slf4j
public class TVideoServiceImpl extends ServiceImpl<TVideoMapper, TVideo> implements TVideoService {

    @Resource
    private TVideoMapper tVideoMapper;

    @Override
    public Map<String,Object> uploadVideo() {
        VodUploadClient client = new VodUploadClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        VodUploadRequest request = new VodUploadRequest();
        //视频本地地址 / 使用方法参数filePath代替硬编码的文件路径
        request.setMediaFilePath("C:\\Users\\86136\\Pictures\\Uplay\\video\\kecheng.mp4");
        //指定任务流
        request.setProcedure("LongVideoPreset");
        try {
            VodUploadResponse response = client.upload("ap-guangzhou", request);
            //获取上传后的视频id和地址
            final Map<String, Object> videoMap = new HashMap<>();
            videoMap.put("fileId",response.getFileId());
            videoMap.put("mediaUrl",response.getMediaUrl());
            return videoMap;
        } catch (Exception e) {
            log.info("------{}",String.valueOf(e));
            // 业务方进行异常处理
            return null;
        }
    }

    //删除视频
    @Override
    public void removeVideo(String fileId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            Credential cred = new Credential(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, "");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(fileId);
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(DeleteMediaResponse.toJsonString(resp));

            tVideoMapper.removeVide(fileId);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    //查询视频
    @Override
    public TVideo getVideo(Long courseId) {
        return tVideoMapper.getVideo(courseId);
    }
}
