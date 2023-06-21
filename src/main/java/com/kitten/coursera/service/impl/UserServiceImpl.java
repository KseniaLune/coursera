package com.kitten.coursera.service.impl;

import com.kitten.coursera.components.ResponseJson;
import com.kitten.coursera.domain.entity.*;
import com.kitten.coursera.domain.exception.FileDownloadEx;
import com.kitten.coursera.domain.exception.FileUploadEx;
import com.kitten.coursera.domain.exception.ResourceMappingEx;
import com.kitten.coursera.domain.exception.ResourceNotFoundEx;
import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.dto.mapper.UserMapper;
import com.kitten.coursera.repo.RoleRepo;
import com.kitten.coursera.repo.UserRepo;
import com.kitten.coursera.repo.UserToCourseRepo;
import com.kitten.coursera.service.UserAvatarService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserToCourseRepo userCourseRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserToCourseRepo userToCourseRepo;
    private final UserAvatarService userAvatarService;

    @Override
    @Transactional
    public AppUser createUser(UserDto dto) {
        var newUser = userMapper.toEntity(dto);

        if (userRepo.findByeMail(newUser.getEMail()).isPresent()) {
            throw new IllegalStateException("User already exist");
        }
        try {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        } catch (Exception e) {
            log.error("problem with encoding password");
        }

        Role roleUser = this.findRole(Role.RoleName.ROLE_STUDENT);

        userRepo.save(newUser);
        newUser.addRolesToUser(roleUser);

        return newUser;
    }

    @Override
    public AppUser getById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<AppUser> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public AppUser updateUser(UUID id, UserDto dto) {
        AppUser user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        if (dto.getNickname() != null) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getEMail() != null) {
            user.setEMail(dto.getEMail());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public ResponseJson deleteBy(UUID id) {
        if (userRepo.findById(id).isEmpty()) {
            return new ResponseJson(null, new ResourceNotFoundEx("This user doesn't exist"));
        }
        try {
            userToCourseRepo.deleteByUserId(id);
            userRepo.deleteById(id);

            return new ResponseJson("User is deleting", null);
        } catch (Exception e) {
            return new ResponseJson(null, new ResourceMappingEx("Error while deleting user."));
        }
    }

    @Override
    public ResponseJson signUp(UUID userId, UUID courseId) {
        if (userRepo.findAllAvailableUsers(courseId).contains(userId)) {
            try {
                userRepo.signUp(userId, courseId);
                return new ResponseJson("Success", null);
            } catch (Exception e) {
                return new ResponseJson(null, new ResourceMappingEx("Error while add data in DB."));
            }
        } else {
            return new ResponseJson("You are already signed up!", null);
        }
    }

    @Override
    public List<Course> findCourseByUserId(UUID userId) {
        return userRepo.findCourseByUserId(userId);
    }

    @Override
    public ResponseJson breakCourse(UUID userId, UUID courseId) {
        UserToCourse userToCourse = userCourseRepo.findByUserIdAndCourseId(userId, courseId);
        if (userToCourse != null) {
            userCourseRepo.delete(userToCourse);
            return new ResponseJson("You leave the course", null);
        } else {
            return new ResponseJson(null, new ResourceMappingEx("Error while deleting data in DB."));
        }
    }

    @Override
    public AppUser findByEMail(String eMail) {
        return userRepo.findByeMail(eMail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public ResponseJson addNewRole(UUID id, Role.RoleName roleName) {
        AppUser user = this.getById(id);
        Role role = this.findRole(roleName);
        try {
            user.addRolesToUser(role);
            userRepo.save(user);
            return new ResponseJson("Role is added", null);
        } catch (Exception e) {
            return new ResponseJson(null, new RuntimeException(e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseJson uploadAvatar(UUID userId, UserAvatar avatar) {
        try {
            AppUser user = this.getById(userId);
            //TODO если аватар есть => удалить предыдущий аватар
            String filename = userAvatarService.addNewAvatar(avatar);
            user.getAvatar().add(filename);
            userRepo.save(user);
            return new ResponseJson("Avatar was uploaded", null);
        } catch (Exception e) {
            return new ResponseJson(null, new FileUploadEx("Avatar wasn't uploaded " + e.getMessage()));
        }
    }
    @Override
    @Transactional
    public ResponseJson showAvatar(UUID userId){
        try {
            AppUser user = this.getById(userId);
            String avatar = user.getAvatar().get(0);
            String url = userAvatarService.showAvatar(avatar);
            return new ResponseJson(url, null);
        } catch (Exception e) {
            return new ResponseJson(null, new FileDownloadEx("Avatar wasn't download " + e.getMessage()));
        }
    }

    private Role findRole(Role.RoleName roleName) {
        return roleRepo.findByRole(roleName)
            .orElseThrow(() -> new RuntimeException("Role doesn't exist"));
    }
}
