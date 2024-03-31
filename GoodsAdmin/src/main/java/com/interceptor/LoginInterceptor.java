package com.interceptor;

import com.service.impl.GoodsServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static Logger log = Logger.getLogger(GoodsServiceImpl.class);

    @Resource(name = "globalStorage")
    Map<String, Object> storage;

    //拦截器--验证登录信息
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求到的结果为:"+request.getSession());
        log.info("存储的结果为:"+storage.get("authSession"));
        if(request.getSession().equals(storage.get("authSession"))){
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("{\"code\":\"010\",\"data\":null,\"message\":\"未登录\"}");
        return false;
    }
}
