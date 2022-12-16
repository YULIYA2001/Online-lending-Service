package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.User;

import java.util.List;


public interface UserService {

    User findByEmailOrPhone(String email, String phone);

    User findByEmail(String email);

    User findByPhone(String phone);

    void createUser(User user);

    String updateUser(User user, String passwordOld);

    List<User> findAllWithDetails();

    User findByIdWithDetails(Long id);

    void deleteById(Long userId);

    void updateUserMakeAdmin(Long userId);
}
