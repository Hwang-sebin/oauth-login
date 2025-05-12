// UserLoginDto.java
package com.oauth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private String id; // ID로 로그인하도록 변경
    private String password;
}
