package com.zhw.blog.dao;

import com.zhw.blog.model.User;

import java.util.List;

public interface UserDao {
    int insert(User record);

    List<User> selectUsers();
}
