package com.kitten.coursera.jwt;

import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
        AppUser user = userService.findByEMail(eMail);

        return JwtEntityFactory.create(user);
    }
}
