package com.evan.wj.mapper;

import com.evan.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMapper extends JpaRepository<Category, Integer> {
}
