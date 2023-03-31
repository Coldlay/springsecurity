package com.liumou.springsecurity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author coldplay
 * @create 2023-03-30 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String password;


}
