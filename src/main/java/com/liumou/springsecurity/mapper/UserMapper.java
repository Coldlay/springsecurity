package com.liumou.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liumou.springsecurity.domain.User;

import java.util.List;

/**
 * @author coldplay
 * @create 2023-03-30 17:29
 */
public interface UserMapper extends BaseMapper<User>{
    List<String> getPermissions(Long id);
}
