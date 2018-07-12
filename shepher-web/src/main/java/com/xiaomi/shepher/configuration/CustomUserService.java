package com.xiaomi.shepher.configuration;

import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;


/**
 *
 * Created by Shawn on 2017/8/15.
 */
@Component
public class CustomUserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(CustomUserService.class);
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(userName)){
            return null;
        }
        try {
            User user = userService.get(userName);
            if (user!=null){
                return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority(WebSecurityConfig.role)));
            }
        } catch (ShepherException e) {
            logger.error("获取用户报错",e);
        }
        return null;
    }
}
