package com.sparta.recipes.service;

import com.sparta.recipes.domain.UserRoleEnum;
import com.sparta.recipes.domain.Users;
import com.sparta.recipes.dto.SignupRequestDto;
import com.sparta.recipes.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    private static final String ADMIN_TOKEN = "12feafDSDSew/reDFEDFew2213/wqe";

    @Autowired /*WebsecurityConfig의 @Bean으로 설정된 BCrypt의 PasswordEncoder 를 DI함*/
    public UsersService(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    /*회원가입*/
    /*관리자 체크 메서드는 아직 구현x */
    public void registerUser(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        /*아이디 중복검사메서드*/
        idDuplicationTest(username);
        /*패스워드 복호화*/
        /*사용자가 입력한 비밀번호 requestDto.getPassword() 가 passwordEncoder.encode 에 의해 암호화됨.*/
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        UserRoleEnum userRole = UserRoleEnum.USER;

        Users user = new Users(username, password, email, userRole);

        usersRepository.save(user);
    }

    /*아이디 중복검사메서드*/
    private void idDuplicationTest(String username) {
        Optional<Users> find = usersRepository.findByUsername(username);
        /*Id 중복검사*/
        if (find.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }
    }

}

