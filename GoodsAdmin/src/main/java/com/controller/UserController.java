package com.controller;

import com.mapper.UserMapper;
import com.pojo.User;
import com.service.interfaces.UserService;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

//    //验证用户名、密码是否正确
//    @GetMapping("/login")
//    public JsonResponse selectUser(String logname, String password){
//
//        return new JsonResponse(JsonResponse.OK, userService.selectUser(logname,password), null);
//    }

    @Resource(name = "globalStorage")
    Map<String, Object> storage;

    //注册
    @GetMapping("/register")
    public JsonResponse register(String username, String password, String picUrl, String address, String phone, String email){
        User user = new User(0,username,password,picUrl,address,phone,email);
        userService.addUser(user);
        //model.addAttribute("msg","注册成功!");
        return new JsonResponse(JsonResponse.OK, null, "注册成功");
    }

    //登录获取用户信息存到seccion
    @GetMapping("/doLogin")
    public JsonResponse doLogin(String username, String password, HttpServletRequest request){
        User getUser = new User(0,username,password,"","","","");
        User user = userService.login(getUser);
        if (user==null){
            return new JsonResponse(JsonResponse.AUTH_ERR, null, "登陆失败");
        }
        HttpSession session = request.getSession();
        session.setAttribute("userId",user.getUserId());
        session.setAttribute("username",user.getUsername());
        session.setAttribute("password",user.getPassword());
        session.setAttribute("user_login",user);
        storage.put("authSession", session);
        return new JsonResponse(JsonResponse.OK, user, "登录成功");
    }


    //修改
    @GetMapping("/update")
    public JsonResponse updateUser(Integer userId,String username, String password, String picUrl, String address, String phone, String email){
        User user = new User(userId,username,password,picUrl,address,phone,email);
        boolean res = userService.updateUser(user);
        return new JsonResponse(JsonResponse.OK, res, "修改成功");
    }

    //删除
    @GetMapping("/delete")
    public JsonResponse delUser(Integer userId){
        userService.delUser(userId);
        return new JsonResponse(JsonResponse.OK, null, "删除成功");
    }

    //注销
    @RequestMapping("/logout")
    public JsonResponse logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("user_login");
        storage.remove("authSession");
        System.out.println("注销成功!");
        return new JsonResponse(JsonResponse.OK, null, "注销成功");
    }

    //根据id查找用户
    @RequestMapping("/findByid")
    public JsonResponse findByid(Integer userId){

        return new JsonResponse(JsonResponse.OK, userService.findByid(userId), "查询成功");
    }

    //根据keyword查找用户
    @RequestMapping("/searchUser")
    public JsonResponse searchUser(String keyword){

        return new JsonResponse(JsonResponse.OK, userService.searchUser(keyword), "查询成功");
    }

    //查询所有用户
    @RequestMapping("/findall")
    public JsonResponse findall(){

        return new JsonResponse(JsonResponse.OK, userService.findall(), "查询成功");
    }

    //根据userId和password查询用户信息
    @RequestMapping("/login")
    public JsonResponse selectUserById(int userId,String password){

        return new JsonResponse(JsonResponse.OK, userService.selectUserById(userId,password), "查询成功");
    }

}
