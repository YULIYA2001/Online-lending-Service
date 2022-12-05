//package com.golubovich.project_trpo_tofi.restcontroller;
//
//import com.golubovich.project_trpo_tofi.model.User;
//import com.golubovich.project_trpo_tofi.repository.UserRepository;
//import com.golubovich.project_trpo_tofi.security.JwtTokenProvider;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//public class AuthenticationRestControllerV1 {
//
//    private final AuthenticationManager authenticationManager;
//    private UserRepository userRepository;
//    private JwtTokenProvider jwtTokenProvider;
//
//    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepository userRepository,
//                                          JwtTokenProvider jwtTokenProvider) {
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
//
//        try {
//            // TODO by phone
//            User user = userRepository.findByEmailOrPhone(request.getPhoneOrEmail(), request.getPhoneOrEmail())
//                    .orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
//            String email = user.getEmail();
//
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    email, request.getPassword()));
//
//            String token = jwtTokenProvider.createToken(email, user.getRole().name());
//            Map<Object, Object> response = new HashMap<>();
//            response.put("email", email);
//            response.put("token", token);
//            return ResponseEntity.ok(response);
//
//        } catch (AuthenticationException e) {
//            return new ResponseEntity<>("Invalid input combination", HttpStatus.FORBIDDEN);
//        }
//    }
//
//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, null);
//    }
//
//}
