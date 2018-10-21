//package com.future.study.spring.security.web;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//
///**
// * @author Dexterleslie.Chan
// */
//public class MyUserDetailsService implements UserDetailsService {
//    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String encodedPassword=passwordEncoder.encode("aa112233");
//        User user=new User(username,encodedPassword, Collections.emptyList());
//        return user;
//    }
//}
