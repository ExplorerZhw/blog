package com.zhw.blog.controller;

import com.zhw.blog.model.Menu;
import com.zhw.blog.service.MenuService;
import com.zhw.blog.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单
 */
@RestController
@RequestMapping(value = "/manage/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/listTree")
    public List<Menu> listTree(HttpServletRequest request) {
        String systemId = request.getParameter("systemId");
        return menuService.listTree(systemId);
    }

    @RequestMapping("/listData")
    public List<Menu> listData(HttpServletRequest request) {
        String menuName = request.getParameter("menuName");
        Menu menu = new Menu();
        menu.setMenuName(menuName);
        return menuService.listData(menu);
    }

    @RequestMapping("/saveOrUpdate")
    public void saveOrUpdate(HttpServletRequest request,Menu menu) {
//        Menu menu = BeanUtil.parseJsonToJavaBean(request, "json", Menu.class);
        if (StringUtils.isNotEmpty(menu.getMenuId())) {
            menuService.update(menu);
        } else {
            menuService.insert(menu);
        }
    }

    @RequestMapping("/deleteById")
    public void deleteById(HttpServletRequest request) {
        String menuId = request.getParameter("menuId");
        menuService.deleteById(menuId);
    }

    @RequestMapping("/getOne")
    public Menu getOne(HttpServletRequest request) {
        String menuId = request.getParameter("menuId");
        if (StringUtils.isEmpty(menuId)) {
            return new Menu();
        } else {
            return menuService.getById(menuId);
        }
    }
}

