package com.tuoguan.tg.vod.service.impl;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.tuoguan.tg.vod.service.FileService;
import com.tuoguan.tg.vod.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

//腾讯云对象存储文件上传
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        String tmpSecretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String tmpSecretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String sessionToken = "TOKEN";
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的地域
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分
        Region region = new Region(ConstantPropertiesUtil.END_POINT); //COS_REGION 参数：配置成存储桶 bucket 的实际地域，例如 ap-beijing，更多 COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 对象键(Key)是对象在存储桶中的唯一标识。
        //获得他原始名称>>去掉uuid的横杠
        String key = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
        //根据当前日期分组
        String dateUrl = new DateTime().toString("yyyy/MM/dd");
        key = dateUrl+"/"+key;

        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());//获取大小防止内存溢出问题

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);

            // 高级接口会返回一个异步结果Upload
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(JSON.toJSONString(putObjectResult));
            //返回上传路径
            //https://ggkt-1304728012.cos.ap-nanjing.myqcloud.com/2023/user/z5.jpg
            return "https://"+bucketName+"."+"cos"+"."+ region.getRegionName()+".myqcloud.com"+"/"+key;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
