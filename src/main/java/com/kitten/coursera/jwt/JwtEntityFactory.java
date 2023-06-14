package com.kitten.coursera.jwt;


import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {
    public static JwtEntity create(AppUser user){
        return new JwtEntity(
            user.getId(),
            user.getEMail(),
            user.getPassword(),
            mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
            .map(Role::getRole)
            .map(Enum::name)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    }
}
