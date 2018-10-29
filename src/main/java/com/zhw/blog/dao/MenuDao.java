package com.zhw.blog.dao;

import com.zhw.blog.model.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> listData(Menu menu);

    int insert(Menu menu);

    void update(Menu menu);

    void deleteById(String menuId);
}
