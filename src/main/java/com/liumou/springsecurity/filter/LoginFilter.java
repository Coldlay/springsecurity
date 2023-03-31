package com.liumou.springsecurity.filter;

import com.liumou.springsecurity.domain.User;
import com.liumou.springsecurity.domain.UserDetail;
import com.liumou.springsecurity.mapper.UserMapper;
import com.liumou.springsecurity.service.UserService;
import com.liumou.springsecurity.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author coldplay
 * @create 2023-03-30 17:52
 */
@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String token = request.getHeader("token");
        //有些页面不需要用户登录（比如登录页面，首页等），所以在filter中，如果没有请求头token，就直接放行。
        //如果遇到了后面需要用户登陆的情况，用户并没有携带token，那么上下文对象中也就不会有当前用户，就会有后面的异常报错等信息。
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return ;
        }


        long id;
        //如果当前用户携带了token，解析并查询该用户，如果添加了redis缓存，直接就在缓存里查询了，相对来说是比较快的
        try {
            Claims claims = JwtUtil.parseJWT(token);
            id = Long.parseLong(claims.getSubject());

        } catch (Exception e) {
            throw new RuntimeException("token异常");
        }

        User user = userMapper.selectById(id);
        if(Objects.isNull(user)){
            throw  new RuntimeException("没登陆呢");
        }

        //走到这一步代表确实存在该用户，此时只需要将查到的用户存入上下文对象中就可
        //因为用户信息在springSecurity中是以UserDetails存在，所以先将其封装成UserDetails的实现类对象UserDetail
        //接着再将其封装成Authentication对象。
        //

        UserDetail userDetail = new UserDetail(user,userMapper.getPermissions(user.getId()));
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetail,userDetail.getPassword(),userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);



    }
}
