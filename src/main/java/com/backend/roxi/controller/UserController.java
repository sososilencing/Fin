package com.backend.roxi.controller;

import com.backend.roxi.bean.User;
import com.backend.roxi.excpetion.MyException;
import com.backend.roxi.mapper.UserMapper;
import com.backend.roxi.service.JWTCeate;
import com.backend.roxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Roxi酱
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/enroll",method = RequestMethod.POST)
    @ResponseBody
    public String enroll(@RequestParam("name") String name,@RequestParam("password") String password){
        userService.enroll(name,password);
        return "恭喜"+name+"注册成功";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("id") String id, @RequestParam("password") String password,HttpServletRequest request) throws MyException {
        String token=userService.verify(id,password);
        if(token==null){
          return "login";
        }

        HttpSession httpSession=request.getSession();
        User user=JWTCeate.verifyToken(token);
        user.setId(user.getId()); user.setName(user.getName());
        httpSession.setAttribute("user",user);
        return "ws";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }

}
