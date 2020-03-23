package com.yss.devtools.demo.controller;


import com.yss.devtools.demo.model.User;
import com.yss.devtools.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lixingjun
 * @Description 演示接口
 * @Date 2020/3/23 下午8:24
 **/
@Api(value = "用户管理演示")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserById/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据用户 id 获取用户信息")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "新增/修改用户信息")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "删除用户信息", notes = "根据用户 id 删除用户信息")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "success";
    }
}
