package com.example.radify_be.persistence;

import com.example.radify_be.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {
    public User save(User user);

    public User findById(Integer id);

    public void deleteById(Integer id);

    public boolean existsById(Integer id);

    public User findByUsername(String username);
}
