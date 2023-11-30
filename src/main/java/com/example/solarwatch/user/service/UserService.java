package com.example.solarwatch.user.service;

import com.example.solarwatch.user.model.User;
import com.example.solarwatch.user.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        Optional<User> optionalUser = findById(id);
        return optionalUser.orElse(null);
    }

    public User getUserByName(String name) {
        Optional<User> optionalUser = userRepository.findByUsername(name);
        return optionalUser.orElse(null);
    }

    public boolean updateUser(User user, int id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User userToUpdate = optionalUser.get();
        if (user.getUsername() != null) {
            userToUpdate.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }
        userRepository.save(userToUpdate);
        return true;
    }

    private Optional<User> findById(int id) {
        return userRepository.findById(id);
    }



}
