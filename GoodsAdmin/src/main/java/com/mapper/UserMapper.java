package com.mapper;

import com.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {


    int selectUser(@Param("logname")String logname, @Param("password")String password);

    //根据ID和密码查询用户信息
    User selectUserById(int userId,String password);

    User login(User user);
    //注册
    int addUser(User user);
    //获取所有用户
    List<User> findall();
    //根据id获取用户
    User findByid(Integer id);
    //根据id更新用户
    int updateUser(User user);
    //根据id删除用户
    int delUser(Integer id);
    //按keyword搜索货物信息
    List<User> searchUser(@Param("keyword") String keyword);


}
