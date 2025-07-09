package org.yaojiu.supermarket.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yaojiu.supermarket.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")) return true;
        if (request.getSession().getAttribute("user") == null) {
            System.out.println("用户未登录");
            String jsonString = new ObjectMapper().writeValueAsString(Result.fail().resetCode(Result.FAIL_NEED_LOGIN).resetMsg("请先登录"));
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json;charset=utf-8");
            writer.write(jsonString);
            writer.flush();
            writer.close();
            return false;
        }
        return true;
    }
}
