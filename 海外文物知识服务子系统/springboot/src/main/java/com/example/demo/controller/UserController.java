package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.common.Result;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.example.demo.common.ResultCode.*;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @CrossOrigin(origins = "http://localhost:6103")
    @PostMapping("/register")//异常:用户名已存在、系统异常
    public Result<?>Register(@RequestBody User user){
        try{
            if(userMapper.findByName(user.getUsername())!=null) {
                throw new UserExistErrorException();
            }
            else if(user.getUsername().isEmpty()|| user.getPassword().isEmpty()) throw new WrongFormatException();
            else{
                LocalDateTime localDateTime = LocalDateTime.now();
                String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                user.setCreateTime(date);
                System.out.println(user.getAvatarUrl());
                userMapper.insert(user);
                return Result.success(userMapper.findByName(user.getUsername()));
            }
        }catch (Exception e){
            if(e instanceof UserExistErrorException) {
                return Result.error(USER_EXIST_ERROR.code, USER_EXIST_ERROR.msg);//用户名已存在异常
            }
            else if(e instanceof WrongFormatException){
                return Result.error(USER_FORMAT_ERROR.code, USER_FORMAT_ERROR.msg);
            }
            return Result.error(ERROR.code, ERROR.msg);//系统异常
        }
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @PostMapping("/login")//登录  存在两种异常:系统异常  用户名和密码不匹配 此处如果未在数据库找到username也为用户名和密码不匹配

    public Result<?> login(@RequestBody User user){
        try{
            if(userMapper.findByName(user.getUsername())==null
                    || !Objects.equals(userMapper.findPasswordByName(user.getUsername()), user.getPassword())){
                throw new NameMatchPasswordException();
            }
            return Result.success(userMapper.findByName(user.getUsername()));
        }catch(Exception e){
            if(e instanceof  NameMatchPasswordException) return Result.error(USER_EXIST_ERROR.code, USER_ACCOUNT_ERROR.msg);
            return Result.error(ERROR.code, ERROR.msg);
        }
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @GetMapping("/user/space/{user_id}")//用户所有评论,通过用户id返回用户信息和所有评论

    //http://localhost:8080/user/space/{user_id}

    public Result<?> User_Space(@PathVariable Integer user_id){
        try{
            User user=userMapper.findById(user_id);
            user.comments=commentMapper.findCommentsByUser_id(user_id);
            return Result.success(user);
        }catch (Exception e){
            return Result.error(ERROR.code, ERROR.msg);
        }
    }

    @CrossOrigin(origins = "http://localhost:6103")
    @PostMapping("/user/modify")//更新信息  异常:系统异常 此处前端不用传回当前时间

    public Result<?> modify_information(@RequestBody User user) {
        try {
            if (userMapper.findByName(user.getUsername()) != null
                    && !Objects.equals(userMapper.findByName(user.getUsername()).getId(), user.getId())) {
                System.out.println("NMSL");
                throw new WrongUserNameException();
            } else {

                LocalDateTime localDateTime = LocalDateTime.now();
                String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                user.setUpdateTime(date);
                userMapper.updateInfo(user);
                commentMapper.updateImageUserName(user);
                User newUser = userMapper.findById(user.getId());
                System.out.println("NMSL1");
                System.out.println(Result.success(newUser));
                return Result.success(newUser);
            }
        } catch (Exception e) {
            if (e instanceof WrongUserNameException) return Result.error(USER_EXIST_ERROR.code, USER_EXIST_ERROR.msg);
            else return Result.error(ERROR.code, ERROR.msg);
        }
    }

}

