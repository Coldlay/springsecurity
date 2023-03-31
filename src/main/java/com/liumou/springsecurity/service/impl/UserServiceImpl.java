package com.liumou.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liumou.springsecurity.domain.ResponseResult;
import com.liumou.springsecurity.domain.User;
import com.liumou.springsecurity.domain.UserDetail;
import com.liumou.springsecurity.domain.vo.UserVO;
import com.liumou.springsecurity.mapper.UserMapper;
import com.liumou.springsecurity.service.UserService;
import com.liumou.springsecurity.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author coldplay
 * @create 2023-03-30 17:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{

    @Autowired(required = false)//为了不让他报错选择加required属性
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        //第一步，将user封装为Authentication对象
        Authentication authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getName(),user.getPassword());
        //第二步,需要进行用户认证，即是查询数据库，和现在的用户对比，
        //因为AuthenticationManager负责认证逻辑，所以需要调用AuthenticationManager的authenticate()方法
        //要想查询数据库去对比，就要自己定义AuthenticationManager,注册bean
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //第三步：封装token，返回给前端
        UserDetail loginUser = (UserDetail)authentication.getPrincipal();
        String jwt = JwtUtil.createJWT(loginUser.getUser().getId().toString());

        //三更这里将token放到了缓存里
        Map<String, String> token = new HashMap<>();
        token.put("token",jwt);

        return new ResponseResult(200,"登陆成功",token);
    }

    @Override
    public ResponseResult logout() {
        SecurityContextHolder.clearContext();


        return new ResponseResult(200,"退出成功");
    }

    @Override
    public ResponseResult getUsers() {
        List<User> users = userMapper.selectList(null);

        return new ResponseResult(200,"获取成功",users);
    }


}
