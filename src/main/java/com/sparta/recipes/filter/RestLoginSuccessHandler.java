package com.sparta.recipes.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.recipes.domain.Users;
import com.sparta.recipes.dto.ResponseDto;
import com.sparta.recipes.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

public class RestLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsersRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


        ResponseDto responseDto = new ResponseDto();

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            /*DB유저와 대조*/
            Users user = userRepository.findByUsername(username).orElse(null);

            if(user == null){
                responseDto.setResult(false);
                responseDto.setErr_msg("없는 회원입니다");
            }else{
                responseDto.setResult(true);
                responseDto.setUsername(user.getUsername());
            }
        }


        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseDto);
        out.flush();


    }

}