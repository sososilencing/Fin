package com.backend.roxi.mapper;

import com.backend.roxi.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author Roxi酱
 */
@Mapper
public interface UserMapper {
    /**
     * dao层 实现 用户注册信息存入数据库
     * @param user
     * @return
     */
    @Insert("insert into user(id,name,password) values(#{id},#{name},#{password})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    boolean addUser(User user);

    /**
     * 登陆验证说明，如果登陆成功 我就颁发token
     * @param user
     * @return User
     */
    @Select("select id,name from user where id=#{id} and password=#{password}")
     User login(User user);
}
