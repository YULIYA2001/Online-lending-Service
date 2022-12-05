//package com.golubovich.project_trpo_tofi.config;
//
//
//import com.golubovich.project_trpo_tofi.model.User;
//import com.golubovich.project_trpo_tofi.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
////import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import javax.swing.tree.RowMapper;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/*", "/about", "/signup").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                .and()
//                    .logout()
//                    .permitAll();
//
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select email, phone password from users where email=? or phone=?1")
//                .authoritiesByUsernameQuery("select email, role from users where email=? or phone=?1");
//
//    }
//
////    @Service
////    public class CustomUserDetailService implements UserDetailsService {
////        @Autowired
////        UserRepository userRepository;
////
////        @Override
////        public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
////            final User user;
////            User user1;
////            user1 = userRepository.findByEmail(emailOrPhone);
////            if (user1 == null) {
////                user1 = userRepository.findByPhone(emailOrPhone);
////                if (user1 == null) {
////                    throw new UsernameNotFoundException(emailOrPhone);
////                }
////            }
////            user = user1;
////            boolean enabled = !user.isAccountVerified();
////            UserDetails userDetails = User.withUsername(user.getEmail())
////            .password(user.getPassword())
////            .disabled(enabled)
////            .authorities("USER").build();
////
////            return userDetails;
////        }
////    }
//
//}
