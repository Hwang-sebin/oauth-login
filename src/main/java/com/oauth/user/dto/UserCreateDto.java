// UserCreateDto.java
package com.oauth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String id; // 로그인 ID
    private String password;
    private String gender;
    private String email;
    private String profile_image;
    private String job;
    private BigDecimal income;
    private Date moveInDate;
}
