package com.oauth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDto {
    private String id; // 로그인 아이디
    private String gender; // male, female, other
    private String profile_image;
    private String job;
    private BigDecimal income;
    private Date move_in_date;
    // password 필드는 별도 API로 처리하는 것이 좋습니다
}
