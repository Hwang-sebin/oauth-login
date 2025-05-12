package com.oauth.user.service;

import com.oauth.user.domain.User;
import com.oauth.user.domain.SocialType;
import com.oauth.user.dto.UserCreateDto;
import com.oauth.user.dto.UserLoginDto;
import com.oauth.user.dto.UserProfileUpdateDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.oauth.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mongoTemplate = mongoTemplate;
    }

    public User create(UserCreateDto userCreateDto) {

        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .id(userCreateDto.getId())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .gender(userCreateDto.getGender())
                .job(userCreateDto.getJob())
                .income(userCreateDto.getIncome())
                .profile_image(userCreateDto.getProfile_image())
                .move_in_date(userCreateDto.getMoveInDate())
                .quiz_score(0)
                .quiz_rank(0)
                .created_at(new Date())
                .updated_at(new Date())
                .build();
        userRepository.save(user);
        return user;
    }

    public User login(UserLoginDto userLoginDto) {
        // 변경: findById() 대신 findByEmail()을 사용
        Optional<User> optUser = userRepository.findByEmail(userLoginDto.getId());
        if (!optUser.isPresent()) {
            throw new IllegalArgumentException("ID가 존재하지 않습니다.");
        }

        User user = optUser.get();
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password가 일치하지 않습니다.");
        }
        return user;
    }


    // 소셜 로그인 관련 메서드 유지
    public User getUserBySocialId(String socialId) {
        return userRepository.findBySocialId(socialId).orElse(null);
    }

    public User createOauth(String socialId, String email, SocialType socialType) {
        User user = User.builder()
                .email(email)
                .socialType(socialType)
                .socialId(socialId)
                .role("USER")
                .quiz_score(0)
                .quiz_rank(0)
                .created_at(new Date())
                .updated_at(new Date())
                .build();
        userRepository.save(user);
        return user;
    }

    // 프로필 업데이트 메서드 추가
    public User updateProfile(String userId, UserProfileUpdateDto profileUpdateDto) {
        // MongoDB 부분 업데이트를 위해 Query와 Update 객체 생성
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update();

        // 제공된 필드만 업데이트
        if (profileUpdateDto.getId() != null) {
            update.set("id", profileUpdateDto.getId());
        }
        if (profileUpdateDto.getGender() != null) {
            update.set("gender", profileUpdateDto.getGender());
        }
        if (profileUpdateDto.getProfile_image() != null) {
            update.set("profile_image", profileUpdateDto.getProfile_image());
        }
        if (profileUpdateDto.getJob() != null) {
            update.set("job", profileUpdateDto.getJob());
        }
        if (profileUpdateDto.getIncome() != null) {
            update.set("income", profileUpdateDto.getIncome());
        }
        if (profileUpdateDto.getMove_in_date() != null) {
            update.set("move_in_date", profileUpdateDto.getMove_in_date());
        }

        // 업데이트 시간 갱신
        update.set("updated_at", new Date());

        // findAndModify를 사용하여 업데이트 후 업데이트된 문서 반환
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsById(String id) {
        return userRepository.findById(id).isPresent();
    }

    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }




}
