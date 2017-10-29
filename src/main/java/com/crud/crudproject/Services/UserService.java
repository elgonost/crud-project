package com.crud.crudproject.Services;

import com.crud.crudproject.Forms.Login;
import com.crud.crudproject.Models.User;
import com.crud.crudproject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User register(User user){
        return userRepository.save(user);
    }

    public User find(Long id){
        return userRepository.getOne(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User authenticate(Login login){
        User user = getUserByUsername(login.getUsername());
        if(user != null && user.getPassword().equals(login.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
