package com.evan.wj.service;

import com.evan.wj.mapper.CategoryMapper;
import com.evan.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> list() {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryMapper.findAll();
    }

    public Category get(int id) {
        Category c= categoryMapper.findById(id).orElse(null);
        return c;
    }
}
