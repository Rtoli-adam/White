package com.evan.wj.service;

import com.evan.wj.mapper.UserMapper;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public boolean isExits(String username){
        User user = getByName(username);
        return null != user;
    }

    public User getByName(String username){
        return userMapper.findByUsername(username);
    }

    public User get(String username, String password){
        return userMapper.getByUsernameAndPassword(username, password);
    }

    public void add(User user){
        userMapper.save(user);
    }
}
