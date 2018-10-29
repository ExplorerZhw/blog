package com.zhw.blog.service;

import com.zhw.blog.model.Label;

import java.util.List;

public interface LabelService {
    int addLabel(Label label);

    List<Label> findAllLabel();

    Label getOne(String lableId);

    void update(Label label);
}
