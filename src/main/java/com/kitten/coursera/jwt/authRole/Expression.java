package com.kitten.coursera.jwt.authRole;

import com.kitten.coursera.domain.entity.Role;
import com.kitten.coursera.jwt.JwtEntity;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class Expression {
    private final UserService userService;

    public boolean canAccessUser(UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        UUID userId = user.getId();

        return userId.equals(id) ||hasAnyRole(authentication, Role.RoleName.ROLE_ADMIN, Role.RoleName.ROLE_OWNER);
    }

    private boolean hasAnyRole(Authentication authentication, Role.RoleName... roles) {
        for(Role.RoleName role:roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if(authentication.getAuthorities().contains(authority)){
                return true;
            }
        }
        return false;
    }
    public boolean canAccessLesson(UUID lessonId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        UUID userId = user.getId();

        return userService.isUserSigned(userId, lessonId) || hasAnyRole(authentication, Role.RoleName.ROLE_PROFESSOR, Role.RoleName.ROLE_ADMIN, Role.RoleName.ROLE_OWNER);
    }
}
