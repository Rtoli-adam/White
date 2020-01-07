package com.evan.wj.controller;

import com.evan.wj.pojo.User;
import com.evan.wj.result.Result;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {

        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(usernamePasswordToken);
        }catch (Exception e){
            String message = "账号或密码错误";
            return ResultFactory.buildFailResult(message);
        }
//        User user = userService.getByName(username);
//        if (user == null) {
//            String message = "账号不存在，请前往注册";
//            return new Result(400,message,"");
//        } else {
//            String encodePwd = new SimpleHash("md5",password,user.getSalt(),3).toString();
//            if(user.getPassword().equals(encodePwd)){
//                return new Result(200,"","");
//            }
//            return new Result(400,"账号或密码不正确","");
//        }
    }

    @PostMapping("api/register")
    @ResponseBody
    public Result register(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);

        boolean exit = userService.isExits(username);
        if (exit){
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }
        // 生成盐
        String salt = UUID.randomUUID().toString();
        // 设置 hash 算法迭代次数
        int times = 3;
        // 得到 hash 后的密码
        String encodePwd = new SimpleHash("md5",password,salt,times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        user.setSalt(salt);
        user.setPassword(encodePwd);
        userService.add(user);

        return ResultFactory.buildSuccessResult(user);
    }
}
