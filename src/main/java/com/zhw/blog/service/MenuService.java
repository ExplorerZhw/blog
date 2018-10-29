package com.zhw.blog.service;

import com.zhw.blog.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> listData(Menu menu);

    Menu getById(String menuId);

    int insert(Menu menu);

    void update(Menu menu);

    int deleteById(String menuId);

    List<Menu> listTree(String systemId);
}
