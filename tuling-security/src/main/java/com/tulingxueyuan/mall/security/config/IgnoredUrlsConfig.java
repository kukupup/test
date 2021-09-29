package com.tulingxueyuan.mall.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
//自动扫配置信息中的secure.ignored打头的配置信息把配置放入urls
@ConfigurationProperties(prefix = "secure.ignored")
public class  IgnoredUrlsConfig {
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
