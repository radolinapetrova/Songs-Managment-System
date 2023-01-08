package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.UserService;
import com.example.radify_be.bussines.exceptions.DublicateDataException;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.UserRepo;

import com.example.radify_be.security.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo repo;

    private final PasswordEncoder passwordEncoder;


    @Override
    public User getById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void validateEmail(String email) throws InvalidInputException {

        Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
        Matcher match = pattern.matcher(email);

        if (!match.matches()) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void deleteUser(Integer id) throws InvalidInputException {
        if (!repo.existsById(id)) {
            throw new InvalidInputException();
        }

        repo.deleteById(id);
    }

    @Override
    public User updateUser(User user) throws UnsuccessfulAction, InvalidInputException {
        validateEmail(user.getAccount().getEmail());

        User og = repo.findById(user.getId());
        user.setRole(og.getRole());
        user.getAccount().setPassword(og.getAccount().getPassword());

        User updated = repo.save(user);

        if (updated.equals(og)) {
            throw new UnsuccessfulAction();
        }

        return updated;
    }

    @Override
    public User register(User user) throws InvalidInputException, DublicateDataException {
        validateEmail(user.getAccount().getEmail());
        user.getAccount().setPassword(passwordEncoder.encode(user.getAccount().getPassword()));
        User result = null;
        try {
            result = repo.save(user);
            log.info("epa i tuka wa");
        } catch (Exception e) {
            log.info("Exc is {}", e.getClass());
            throw new DublicateDataException();
        }

        return result;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new CustomUser(user.getId(), user.getAccount().getUsername(), user.getAccount().getPassword(), authorities);
    }
}
