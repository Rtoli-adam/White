package com.evan.wj.mapper;

import com.evan.wj.pojo.User;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapping
public interface UserMapper extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User getByUsernameAndPassword(String username, String password);
}
