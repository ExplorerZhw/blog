package com.zhw.blog.service;

import com.github.pagehelper.PageInfo;
import com.zhw.blog.model.User;

public interface UserService {
    int addUser(User user);

    PageInfo<User> findAllUser(int pageNum, int pageSize);
}
