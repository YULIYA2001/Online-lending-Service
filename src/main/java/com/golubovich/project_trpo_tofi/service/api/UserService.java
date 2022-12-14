package com.golubovich.project_trpo_tofi.service.api;

import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User findByEmailOrPhone(String email, String phone);

    User findByEmail(String email);

    User findByPhone(String phone);

    void createUser(User user);

    String updateUser(User user, String passwordOld);

    List<User> findAllRoleUserWithDetails();

    User findByIdWithDetails(Long id);
}
