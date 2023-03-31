package com.liumou.springsecurity.controller;

import com.liumou.springsecurity.domain.ResponseResult;
import com.liumou.springsecurity.domain.User;
import com.liumou.springsecurity.domain.vo.UserVO;
import com.liumou.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author coldplay
 * @create 2023-03-30 17:35
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        return userService.login(user);
    }

    @GetMapping("/test")
    public ResponseResult test(){
        return new ResponseResult(200,"认证成功啦");
    }

    @GetMapping("/logout")
    public ResponseResult logout(){
       return  userService.logout();
    }
}
