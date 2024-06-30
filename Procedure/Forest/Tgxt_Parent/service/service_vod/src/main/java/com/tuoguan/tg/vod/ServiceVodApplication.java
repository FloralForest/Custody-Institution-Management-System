package com.tuoguan.tg.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
////开启注册功能使用nacos
//@EnableDiscoveryClient
public class ServiceVodApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class , args);
        System.out.println("ServiceVodApplication---启动成功！");
    }
}
