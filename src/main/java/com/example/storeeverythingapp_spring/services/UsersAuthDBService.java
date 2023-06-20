package com.example.storeeverythingapp_spring.services;

import com.example.storeeverythingapp_spring.data.db.UserEntity;
import com.example.storeeverythingapp_spring.repositories.db.AuthoritiesEntityRepository;
import com.example.storeeverythingapp_spring.repositories.db.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UsersAuthDBService implements UserDetailsService {
    @Autowired
    UserEntityRepository users;
    @Autowired
    AuthoritiesEntityRepository authorities;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = users.findByUsername(username) != null ? users.findByUsername(username) : null;
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " not found !");
        else {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    authorities.findByUsername(username)
                            .stream()
                            .map(role -> {
                                System.out.println("autorities " + role.getAuthority());
                                return new SimpleGrantedAuthority(role.getAuthority());
                            })
                            .collect(Collectors.toSet()));
            return userDetails;
        }
    }
}

