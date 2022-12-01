package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.UserService;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.UserRepo;

import com.example.radify_be.security.CustomUser;
import com.example.radify_be.security.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo repo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) throws Exception {
        validateEmail(user.getAccount().getEmail());
        user.getAccount().setPassword(passwordEncoder.encode(user.getAccount().getPassword()));
        repo.save(user);
    }

    @Override
    public User getById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void validateEmail(String email) throws Exception {

        Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
        Matcher match = pattern.matcher(email);

        if (!match.matches()) {
            throw new Exception("Wrong email");
        }
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        repo.deleteById(id);

        if (repo.existsById(id)) {
            throw new Exception("Unsuccessful deletion of account!");
        }
    }

    public User updateUser(User user) throws Exception {
        return repo.save(user);
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found in the database!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        //return new org.springframework.security.core.userdetails.User(user.getAccount().getUsername(),user.getAccount().getPassword(),authorities);
        return new CustomUser(user.getId(), user.getAccount().getUsername(),user.getAccount().getPassword(),authorities);
    }
}
