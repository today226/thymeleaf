package com.denis.thymeleaf.thymeleaf.service;

import com.denis.thymeleaf.thymeleaf.model.Role;
import com.denis.thymeleaf.thymeleaf.model.User;
import com.denis.thymeleaf.thymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {

        String userPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(userPassword);
        user.setEnabled(true);
        Role role = new Role(); //나중에 레파지토리 만들어서 롤을 검색하는 기능을 통해서 넣자
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
