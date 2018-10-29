package com.zhw.blog.service.impl;

import com.zhw.blog.dao.UserDao;
import com.zhw.blog.model.User;
import com.zhw.blog.service.UserService;
import com.zhw.blog.util.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public List<User> listUser(User user) {
        return userDao.listUser(user);
    }

    public boolean examineLogin(User user) {
        List<User> userList = listUser(user);
        boolean result = false;
        if (ListUtils.isNotEmpty(userList) && userList.get(0).getUserId() != null) {
            result = true;
        }
        return result;
    }
}