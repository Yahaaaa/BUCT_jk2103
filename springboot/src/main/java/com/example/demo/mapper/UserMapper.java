package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Select("Select * from user where id=#{id}")
    public User findById(Integer id);
    @Update("update user set " +
            "username=#{username}," +
            "password=#{password}," +
            "avatar_url=#{avatarUrl}," +
            "email=#{email}," +
            "phone=#{phone}," +
            "update_time=#{updateTime}," +
            "sex=#{sex}," +
            "age=#{age} " +
            "where id=#{id}")
    public void updateInfo( User user);
    @Insert("Insert into user(username,password,create_time,isbanned,avatar_url) values (#{username},#{password},#{createTime},#{isBanned},'https://img.touxiangwu.com/zb_users/upload/2023/04/202304061680747835345417.jpg')")
    public void insert(User user);
    @Select("select * from user where username=#{username};")
    public User findByName(String username);
    @Select("select password from user where username=#{username};")
    public String findPasswordByName(String username);
    @Select("select password from user where id=#{id};")
    public String findPasswordById(Integer id);
    @Update("update user set isbanned=true where id=#{UserId} ")
    public void updateIsBannedByUserId(Integer UserId);
}
