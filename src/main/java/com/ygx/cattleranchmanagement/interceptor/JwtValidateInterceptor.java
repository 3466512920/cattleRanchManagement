package com.ygx.cattleranchmanagement.interceptor;

import com.alibaba.fastjson.JSON;
import com.ygx.cattleranchmanagement.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");

        // 日志打印
        log.debug(request.getRequestURI() +" 待验证："+token);
        if(token != null){
            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI() + " 放行...");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 日志打印
        log.debug(request.getRequestURI() + " 禁止访问...");

        // 返回给前端的格式
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(20003,"jwt令牌无效，请重新登录")));
        return false;
    }
}
