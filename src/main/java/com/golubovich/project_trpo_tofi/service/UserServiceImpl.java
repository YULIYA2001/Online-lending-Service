package com.golubovich.project_trpo_tofi.service;

import com.golubovich.project_trpo_tofi.model.Bank;
import com.golubovich.project_trpo_tofi.model.Role;
import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import com.golubovich.project_trpo_tofi.security.UserDetailsServiceImpl;
import com.golubovich.project_trpo_tofi.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl detailsService;
    private final BankServiceImpl bankService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserDetailsServiceImpl detailsService, BankServiceImpl bankService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.detailsService = detailsService;
        this.bankService = bankService;
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

    public String updateUser(User user, String passwordOld) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User old = this.findByEmail(authentication.getName());

        if (old != null) {
            if (!Objects.equals(old.getEmail(), user.getEmail())) {
                if (this.findByEmail(user.getEmail()) != null) {
                    return "email";
                }
            }
            if (!Objects.equals(old.getPhone(), user.getPhone())) {
                if (this.findByPhone(user.getPhone()) != null) {
                    return "phone";
                }
            }
            if (!Objects.equals(passwordOld, "")) {
                if (!passwordEncoder.matches(passwordOld, old.getPassword())) {
                    return "password";
                }
                if (!Objects.equals(user.getPassword(), "")) {
                    old.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            }

            String oldEmail = old.getEmail();

            old.setEmail(user.getEmail());
            old.setPhone(user.getPhone());
            old.getUserDetails().setSurname(user.getUserDetails().getSurname());
            old.getUserDetails().setName(user.getUserDetails().getName());
            old.getUserDetails().setPatronymic(user.getUserDetails().getPatronymic());
            old.getUserDetails().setAge(user.getUserDetails().getAge());
            old.getUserDetails().setPassportID(user.getUserDetails().getPassportID());

            userRepository.save(old);

            if (!Objects.equals(oldEmail, old.getEmail())) {
                UserDetails userDetails = detailsService.loadUserByUsername(user.getEmail());
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        return "";
    }

    public List<User> findAllWithDetails() {
        return userRepository.findAllWithDetails("USER", "ADMIN");
    }

    public User findByIdWithDetails(Long id) {
        return userRepository.findByIdWithDetails(id);
    }

    public void deleteById(Long userId) {
        User user = this.findByIdWithDetails(userId);
        if (user.getRole() == Role.USER) {
            userRepository.deleteById(userId);
        }
        else {
            bankService.deleteBankByAdminId(userId);
        }

    }

    public void updateUserMakeAdmin(Long userId) {
        User user = this.findByIdWithDetails(userId);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
