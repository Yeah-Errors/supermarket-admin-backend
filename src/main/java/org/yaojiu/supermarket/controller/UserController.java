package org.yaojiu.supermarket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaojiu.supermarket.entity.Result;
import org.yaojiu.supermarket.entity.UserDTO;
import org.yaojiu.supermarket.entity.UserEntity;
import org.yaojiu.supermarket.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/login")
    public Result login(@NotNull @Valid @RequestBody UserEntity userEntity, HttpSession session){
        UserDTO loginUser = userService.login(userEntity);
        session.setAttribute("user", loginUser);
        return Result.success().resetData(loginUser);
    }
    @PostMapping(value = "/reg")
    public Result reg(@NotNull @Valid @RequestBody UserEntity userEntity){
        if (userService.register(userEntity)) return Result.success();
        return Result.fail().resetMsg("注册失败");
    }
    @PostMapping(value = "/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return Result.success().resetMsg("注销成功");
    }
}
