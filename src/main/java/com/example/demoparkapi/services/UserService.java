package com.example.demoparkapi.services;

import com.example.demoparkapi.entities.User;
import com.example.demoparkapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public User findById(Long id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseThrow(()-> new RuntimeException ("User not found"));
    }
    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if(!newPassword.equals(confirmPassword)){
            throw new RuntimeException("Passwords don't match");
        }

        User user = findById(id);

        if(!user.getPassword().equals(currentPassword)){
            throw new RuntimeException("Current password doesn't match");
        }

        user.setPassword(newPassword);
        return user;
    }
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
