package com.oauth.user.domain;

import com.oauth.user.domain.SocialType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "users") // collection 이름 변경
public class User {
    @Id
    private String user_id; // 기본 식별자

    private String id; // 로그인용 아이디
    private String password;
    private String gender; // enum('male', 'female', 'other')
    private String email;
    private String profile_image;
    private String job;
    private BigDecimal income;
    private Date move_in_date;
    private Integer quiz_score;
    private Integer quiz_rank;

    @Builder.Default
    private String role = "USER";

    private SocialType socialType; // 소셜 로그인 타입 유지
    private String socialId; // 소셜 ID 유지

    @Builder.Default
    private Date created_at = new Date();

    @Builder.Default
    private Date updated_at = new Date();
}
