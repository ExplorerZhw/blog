package com.zhw.blog.service.impl;

import com.zhw.blog.dao.MenuDao;
import com.zhw.blog.model.Menu;
import com.zhw.blog.service.MenuService;
import com.zhw.blog.util.ListUtils;
import com.zhw.blog.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    public List<Menu> listData(Menu menu) {
        return menuDao.listData(menu);
    }

    @Override
    public int insert(Menu menu) {
        menu.setMenuId(UUIDUtil.getUuidStr());
        menuDao.insert(menu);
        return 0;
    }

    @Override
    public void update(Menu menu) {
        if (StringUtils.isNotEmpty(menu.getMenuId()) && StringUtils.isNotEmpty(menu.getfId())
                && StringUtils.isNotEmpty(menu.getMenuName())) {
            menuDao.update(menu);
        }
    }

    @Override
    public int deleteById(String menuId) {
        // 校验ID是否为空；1-更新失败
        int flag = 0;
        if (StringUtils.isNotEmpty(menuId)) {
            menuDao.deleteById(menuId);
        } else {
            flag = 1;
        }
        return flag;
    }

    @Override
    public List<Menu> listTree(String systemId) {
        Menu query = new Menu();
        query.setSystemId(systemId);
        List<Menu> list = listData(query);
        if (ListUtils.isNotEmpty(list)) {
            for (Menu menuTemp : list) {
                query = new Menu();
                query.setfId(menuTemp.getMenuId());
                List<Menu> listTemp = listData(query);
                if (ListUtils.isNotEmpty(listTemp)) {
                    menuTemp.setMenuList(listTemp);
                }
            }
        }
        return list;
    }

    @Override
    public Menu getById(String menuId) {
        Menu menu = new Menu();
        menu.setMenuId(menuId);
        List<Menu> list = listData(menu);
        if (ListUtils.isNotEmpty(list)) {
            menu = list.get(0);
        } else {
            menu = new Menu();
        }
        return menu;
    }
}
