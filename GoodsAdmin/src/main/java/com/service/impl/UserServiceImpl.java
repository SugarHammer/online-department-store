package com.service.impl;

import com.mapper.UserMapper;
import com.pojo.User;
import com.service.interfaces.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int selectUser(@Param("logname")String logname, @Param("password")String password){

        userMapper.selectUser(logname,password);
            return 1;
    }

    //登录判断
    public User login(User user){
        return userMapper.login(user);
    }
    //注册
    public boolean addUser(User user){
        int i = userMapper.addUser(user);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }
    //获取所有用户
    public List<User> findall(){
        return userMapper.findall();
    }
    //根据userId获取用户
    public User findByid(Integer userId){
        return  userMapper.findByid(userId);
    }
    //根据userId更新用户
    public boolean updateUser(User user){
        int i = userMapper.updateUser(user);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }
    //根据userId删除用户
    public int delUser(Integer userId){
        return userMapper.delUser(userId);
    }

    //按keyword搜索货物信息
    public List<User> searchUser(String keyword){
        return userMapper.searchUser(keyword);
    }

    public User selectUserById(int userId,String password){
        return userMapper.selectUserById(userId, password);
    }

}