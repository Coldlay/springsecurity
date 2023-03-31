package com.liumou.springsecurity.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author coldplay
 * @create 2023-03-30 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long id;

    private String userName;

    private String token;
}
