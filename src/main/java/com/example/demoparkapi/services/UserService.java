package com.example.demoparkapi.services;

import com.example.demoparkapi.entities.User;
import com.example.demoparkapi.entities.enums.Role;
import com.example.demoparkapi.repositories.UserRepository;
import com.example.demoparkapi.services.exceptions.EntityNotFoundException;
import com.example.demoparkapi.services.exceptions.PasswordInvalidException;
import com.example.demoparkapi.services.exceptions.UserNameUniqueViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            throw new UserNameUniqueViolationException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseThrow(()-> new EntityNotFoundException(id));
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if(!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("Passwords don't match");
        }

        User user = findById(id);

        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new PasswordInvalidException("Current password doesn't match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        return optional.orElseThrow(()-> new EntityNotFoundException(username));
    }
    @Transactional(readOnly = true)
    public Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
