package com.zhw.blog.service;

import com.zhw.blog.model.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    List<User> listUser(User user);

    boolean examineLogin(User user);
}
