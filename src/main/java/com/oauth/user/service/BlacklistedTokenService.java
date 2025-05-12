package com.oauth.user.service;

import com.oauth.user.domain.BlacklistedToken;
import com.oauth.user.repository.BlacklistedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class BlacklistedTokenService {
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final String secretKey;

    public BlacklistedTokenService(BlacklistedTokenRepository blacklistedTokenRepository,
                                   @Value("${jwt.secret}") String secretKey) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
        this.secretKey = secretKey;
    }

    public void blacklistToken(String token) {
        // 토큰에서 만료일 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expiryDate = claims.getExpiration();

        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .expiryDate(expiryDate)
                .build();

        blacklistedTokenRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    // 만료된 토큰 정리를 위한 스케줄링 작업
    @Scheduled(cron = "0 0 * * * *") // 매시간 실행
    public void cleanupExpiredTokens() {
        Date now = new Date();
        blacklistedTokenRepository.findAll().stream()
                .filter(token -> token.getExpiryDate().before(now))
                .forEach(blacklistedTokenRepository::delete);
    }
}
