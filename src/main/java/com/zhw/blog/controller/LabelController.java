package com.zhw.blog.controller;

import com.zhw.blog.model.Label;
import com.zhw.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @ResponseBody
    @GetMapping("/add")
    public int addUser(Label label) {
        return labelService.addLabel(label);
    }

    @ResponseBody
    @GetMapping("/all")
    public List<Label> findAllUser() {
        return labelService.findAllLabel();
    }
}

