package com.backend.roxi.service;

import com.backend.roxi.bean.User;
import com.backend.roxi.mapper.UserMapper;
import com.backend.roxi.utils.ToCast;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author Roxi酱
 */
@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    /**
     *  注册服务层 实现 注册逻辑
     * @param name
     * @param password
     */
    public void enroll(String name,String password){
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        System.out.println(userMapper.addUser(user));
    }

    /**
     * 校验是否登陆成功
     * @param id
     * @param password
     * @return 是否登陆成功
     */
    public String verify(@NotNull String id,@NotNull String password){
        User user=new User();
        user.setId(ToCast.intCast(id));
        user.setPassword(password);
        user=userMapper.login(user);
        if(user==null){
            return null;
        }
        return JWTCeate.createToken(user);
    }
}
