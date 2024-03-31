package com.controller;

import com.configuration.LoginProperties;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;



@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginProperties properties;

    @Resource(name = "globalStorage")
    Map<String, Object> storage;

    @RequestMapping("/doLogin")
    public JsonResponse doLogin(String username, String password, HttpSession session){
        if(properties.getAdminName().equals(username) && properties.getAdminPass().equals(password)){
            storage.put("authSession", session);
            return new JsonResponse(JsonResponse.OK, null, "登录成功");
        } else {
            return new JsonResponse(JsonResponse.AUTH_ERR, null, "登陆失败");
        }
    }

    @RequestMapping("/checkLogin")
    public JsonResponse checkLogin(HttpSession session){
        if (session.equals(storage.get("authSession"))){
            return new JsonResponse(JsonResponse.OK, null, "已登录");
        } else {
            return new JsonResponse(JsonResponse.AUTH_ERR, null, "未登录");
        }
    }


    @RequestMapping("/logout")
    public JsonResponse logout(){
        storage.remove("authSession");
        return new JsonResponse(JsonResponse.OK, null, "注销成功");
    }
}
