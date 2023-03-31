package com.liumou.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liumou.springsecurity.domain.ResponseResult;
import com.liumou.springsecurity.domain.User;
import com.liumou.springsecurity.domain.vo.UserVO;

/**
 * @author coldplay
 * @create 2023-03-30 17:29
 */
public interface UserService extends IService<User>{
    ResponseResult login(User user);

    ResponseResult logout();

}
