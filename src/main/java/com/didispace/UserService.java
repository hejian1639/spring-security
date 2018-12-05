package com.didispace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description: 自定义登录认证服务实现.
 * @Author: yutaoxu
 * @Date: 2018/5/27
 * @Modified by:
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("前端调用登录认证,username={}", username);
        LoginUser user = new LoginUser();
        user.setUser(username);
        user.setPassword("p");
        return user;

    }
}
