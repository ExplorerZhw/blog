package com.zhw.blog.controller;

import com.zhw.blog.model.Label;
import com.zhw.blog.service.LabelService;
import com.zhw.blog.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@RestController
@RequestMapping(value = "/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping("/add")
    public int addUser(Label label) {
        return labelService.addLabel(label);
    }

    @RequestMapping("/all")
    public List<Label> findAllUser() {
        return labelService.findAllLabel();
    }

    @RequestMapping("/getOne")
    public Label getOne(HttpServletRequest request, HttpServletResponse response) {
        String labelId = request.getParameter("labelId");
        Label label = labelService.getOne(labelId);
        return label;
    }

    @RequestMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Label label = BeanUtil.parseJsonToJavaBean(request, "json", Label.class);
        labelService.update(label);
    }
}

