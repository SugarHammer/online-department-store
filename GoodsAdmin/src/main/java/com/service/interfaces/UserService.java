package com.service.interfaces;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    int selectUser(@Param("logname")String logname, @Param("password")String password);

    //登录判断
    public User login(User user);
    //注册
    public boolean addUser(User user);
    //获取所有用户
    public List<User> findall();
    //根据id获取用户
    public User findByid(Integer userId);
    //根据id更新用户
    public boolean updateUser(User user);
    //根据id删除用户
    public int delUser(Integer userId);
    //按keyword搜索货物信息
    List<User> searchUser(String keyword);
    //根据userID和password查询用户信息
    User selectUserById(int userId,String password);
}
