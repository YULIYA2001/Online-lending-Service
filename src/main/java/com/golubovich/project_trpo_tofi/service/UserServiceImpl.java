package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import com.golubovich.project_trpo_tofi.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmailOrPhone(String email, String phone) {
        List<User> users = userRepository.findByEmailOrPhone(email, phone);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }

    public void createUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

//    Optional<User> findByEmail(String email);
//
//    @Query(value="SELECT * FROM users JOIN user_details ON users.user_details_id = user_details.id WHERE users.role != 'SUPER_ADMIN' ORDER BY users.id",
//            nativeQuery = true)
//    List<User> findAllWithDetails();
//
//    @Query(value="SELECT * FROM users JOIN user_details ON users.user_details_id = user_details.id WHERE users.id = ?1",
//            nativeQuery = true)
//    User findByIdWithDetails(Long id);
}
