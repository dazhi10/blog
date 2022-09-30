package com.nhb.service.impl;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.LoginUser;
import com.nhb.domain.entity.User;
import com.nhb.domain.vo.BlogUserLoginVo;
import com.nhb.domain.vo.UserInfoVo;
import com.nhb.service.BlogLoginService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.JwtUtil;
import com.nhb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户登录实现类
 * @author 大只
 * @date 2022/10/1 01:56
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //账号密码封装成认证对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        //调用认证器的认证方法，传入需要认证的对象
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误!");
        }
        //通过认证，获取用户id，生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //用户的信息存入redis
        redisCache.setCacheObject("blogLogin:" + userId,loginUser);
        //封装userInfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}
