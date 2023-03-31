package com.liumou.springsecurity.test;

import com.liumou.springsecurity.domain.User;
import com.liumou.springsecurity.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author coldplay
 * @create 2023-03-30 17:59
 */
@SpringBootTest
public class TestForPass {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    @Test
    public void test1(){

        String encode = passwordEncoder.encode("123456");

        User user = new User(1000l,"root",encode);

        boolean save = userService.save(user);
        if(save) System.out.println("添加成功");
        else System.out.println("田间失");
    }


}
