package com.golubovich.project_trpo_tofi.security;

import com.golubovich.project_trpo_tofi.model.User;
import com.golubovich.project_trpo_tofi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {
        List<User> users = userRepository.findByEmailOrPhone(phoneOrEmail, phoneOrEmail);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User does not exists");
        }
        User user = users.get(0);
        return SecurityUser.fromUser(user);
    }
}
