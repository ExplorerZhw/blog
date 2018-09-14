package com.zhw.blog.service.impl;

import com.zhw.blog.dao.LabelDao;
import com.zhw.blog.model.Label;
import com.zhw.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 */
@Service(value = "labelService")
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;//这里会报错，但是并不会影响

    @Override
    public int addLabel(Label label) {

        return labelDao.insert(label);
    }

    /*
     *
     */
    @Override
    public List<Label> findAllLabel() {
        return labelDao.selectLabels();
    }
}