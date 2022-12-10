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

//    @Query(value="SELECT * FROM users JOIN user_details ON users.user_details_id = user_details.id WHERE users.role != 'SUPER_ADMIN' ORDER BY users.id",
//            nativeQuery = true)
//    List<User> findAllWithDetails();
//
//    @Query(value="SELECT * FROM users JOIN user_details ON users.user_details_id = user_details.id WHERE users.id = ?1",
//            nativeQuery = true)
//    User findByIdWithDetails(Long id);
}
