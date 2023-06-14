package com.kitten.coursera.service.impl;

import com.kitten.coursera.dto.UserDto;
import com.kitten.coursera.dto.mapper.UserMapper;
import com.kitten.coursera.entity.AppUser;
import com.kitten.coursera.entity.Course;
import com.kitten.coursera.entity.Role;
import com.kitten.coursera.entity.UserToCourse;
import com.kitten.coursera.repo.RoleRepo;
import com.kitten.coursera.repo.UserRepo;
import com.kitten.coursera.repo.UserToCourseRepo;
import com.kitten.coursera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Override
    @Transactional
    public AppUser createUser(UserDto dto) {
        var newUser = userMapper.mapDtoToEntity(dto);

        if (userRepo.findByeMail(newUser.getEMail()).isPresent()){
            throw new IllegalStateException("User already exist");
        }
        try {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }catch (Exception e){
            log.error("problem with encoding password");
        }

        Role roleUser = this.findRole(Role.RoleName.ROLE_STUDENT);

        userRepo.save(newUser);
        newUser.addRolesToUser(roleUser);


//        log.info("newUser: " + newUser.toString());
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
    public void deleteBy(UUID id) {
        userRepo.deleteById(id);
    }

    @Override
    public String signUp(UUID userId, UUID courseId) {
        String result;
        if (userRepo.findAllAvailableUsers(courseId).contains(userId)){
            try {
                userRepo.signUp(userId, courseId);
                result = "Success";
            } catch (Exception e){
                result = "We have problem with BD";
            }
        }else {
            result = "You are already signed up!";
        }

        return result;
    }

    @Override
    public List<Course> findCourseByUserId(UUID userId) {
        return userRepo.findCourseByUserId(userId);
    }

    @Override
    public String breakCourse(UUID userId, UUID courseId) {
        String result;
        UserToCourse userToCourse = userCourseRepo.findByUserIdAndCourseId(userId, courseId);
        if(userToCourse!=null){
            userCourseRepo.delete(userToCourse);
            result = "You leave the course";
        }else{
            result="There is a problem, return later";
        }
        return result;
    }

    @Override
    public AppUser findByEMail(String eMail) {
        return userRepo.findByeMail(eMail).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public String addNewRole(UUID id, Role.RoleName roleName) {
        String result;
        AppUser user = this.getById(id);
        Role role = this.findRole(roleName);
        try {
            user.addRolesToUser(role);
            userRepo.save(user);
        } catch (Exception e){
            result = e.getMessage();
            return result;
        }
        result = "Role is added";
        return result;



    }

    private Role findRole (Role.RoleName roleName){
       return roleRepo.findByRole(roleName)
            .orElseThrow(()->new RuntimeException("Role doesn't exist"));
    }
}
