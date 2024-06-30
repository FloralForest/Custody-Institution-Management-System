package com.tuoguan.tg.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//腾讯云对象存储密钥和地域的工具类（图片）
@Component//注入配置属性
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${tencent.cos.file.region}")
    private String region;

    @Value("${tencent.cos.file.secretId}")
    private String secretId;

    @Value("${tencent.cos.file.secretKey}")
    private String secretKey;

    @Value("${tencent.cos.file.bucketName}")
    private String bucketName;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = region;
        ACCESS_KEY_ID = secretId;
        ACCESS_KEY_SECRET = secretKey;
        BUCKET_NAME = bucketName;
    }
}
