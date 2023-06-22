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
import com.kitten.coursera.service.CourseService;
import com.kitten.coursera.service.LessonService;
import com.kitten.coursera.service.UserAvatarService;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    private final UserToCourseRepo userCourseRepo;
    private final LessonService lessonService;
    private final UserAvatarService userAvatarService;

    private final UserMapper userMapper;

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
    @Transactional
    @Cacheable(value = "UserService::getById", key = "#id")
    public AppUser getById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public AppUser findByEMail(String eMail) {
        return userRepo.findByeMail(eMail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<AppUser> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    @CachePut(value = "UserService::getById", key = "#id")
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
    @CacheEvict(value = "UserService::getById", key = "#id")
    public ResponseJson deleteBy(UUID id) {
        if (userRepo.findById(id).isEmpty()) {
            return new ResponseJson(null, new ResourceNotFoundEx("This user doesn't exist"));
        }
        try {
            userCourseRepo.deleteByUserId(id);
            userRepo.deleteById(id);

            return new ResponseJson("User is deleting", null);
        } catch (Exception e) {
            return new ResponseJson(null, new ResourceMappingEx("Error while deleting user."));
        }
    }

    @Override
    @Cacheable(value = "UserService::signUp", key = "#userId+'.'+#courseId")
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
    @Transactional
    public boolean isUserSigned(UUID userId, UUID lessonId) {
        Course course = lessonService.findBy(lessonId).getCourse();
        List<Course> usersCourse = this.findCourseByUserId(userId);
        if (usersCourse.isEmpty()) {
            return false;
        } else {
            return usersCourse.contains(course);
        }
    }

    @Override
    @Cacheable(value = "UserService::findCourseByUserId", key = "#userId")
    public List<Course> findCourseByUserId(UUID userId) {
        return userRepo.findCourseByUserId(userId);
    }

    @Override
    @CacheEvict(value = "UserService::signUp", key = "#userId+'.'+#courseId")
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
            String filename = userAvatarService.addNewAvatar(avatar);
            log.info("filename = "+filename);
            user.setAvatar(filename);
            userRepo.save(user);
            return new ResponseJson("Avatar was uploaded", null);
        } catch (Exception e) {
            return new ResponseJson(null, new FileUploadEx("Avatar wasn't uploaded " + e.getMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseJson showAvatar(UUID userId) {
        try {
            AppUser user = this.getById(userId);
            String avatar = user.getAvatar();
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
