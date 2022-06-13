package com.sparta.recipes.security;

import com.sparta.recipes.domain.Users;
import com.sparta.recipes.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(username + " 을 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }
}
