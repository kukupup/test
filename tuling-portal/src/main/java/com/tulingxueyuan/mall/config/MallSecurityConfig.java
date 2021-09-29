package com.tulingxueyuan.mall.config;

import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import com.tulingxueyuan.mall.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Configuration
@EnableWebSecurity  // 启动
public class MallSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsMemberService memberService;

    /**
     * 认证交给springsecurity
     * @return
     */
    //函数式接口直接返回接口的方法实际返回的类型是Userdetails相当于直接实现了该方法UserDetailsService.loadUserByUsername
    //这里没有告诉springSecurity是哪个页面所以无法获取username。这里改写了从memberService中获取 大概
    //查询数据库用户名密码正确才会返回对象，然后返回username。交给springsecurity，
    //如果是登录过就交给jwt的令牌处理，然后在交给springsecurity
          //里面的数据相当于已经查询过了，可以返回user也可以返回UserDetails
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> memberService.loadUserByUsername(username);
    }

}
