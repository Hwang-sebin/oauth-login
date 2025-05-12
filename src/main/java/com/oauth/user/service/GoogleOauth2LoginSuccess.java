/*
package com.oauth.user.service;

import com.oauth.common.auth.JwtTokenProvider;
import com.oauth.user.domain.user;
import com.oauth.user.domain.SocialType;
import com.oauth.user.repository.userRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleOauth2LoginSuccess extends SimpleUrlAuthenticationSuccessHandler {
    private final userRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public GoogleOauth2LoginSuccess(userRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

//        oauth프로필 추출
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String openId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
//        회원가입 여부 확인
        user user = userRepository.findBySocialId(openId).orElse(null);
        if(user == null){
            user = user.builder()
                    .socialId(openId)
                    .email(email)
                    .socialType(SocialType.GOOGLE)
                    .build();
            userRepository.save(user);
        }
//        jwt토큰 생성
        String jwtToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().toString());

//        클라이언트 redirect 방식으로 토큰 전달
//        response.sendRedirect("http://localhost:3000?token="+jwtToken);

        Cookie jwtCookie = new Cookie("token", jwtToken);
        jwtCookie.setPath("/"); //모든 경로에서 쿠키 사용가능
        response.addCookie(jwtCookie);
        response.sendRedirect("http://localhost:3000");

    }

}
*/
