package com.yss.devtools.demo.service;


import com.yss.devtools.demo.model.User;

import java.util.List;


public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteById(Long id);
}
