package com.zhw.blog.dao;

import com.zhw.blog.model.Label;
import com.zhw.blog.model.User;

import java.util.List;

public interface LabelDao {
    int insert(Label label);

    List<Label> selectLabels();

    Label getByLabelId(String lableId);

    void update(Label label);
}
