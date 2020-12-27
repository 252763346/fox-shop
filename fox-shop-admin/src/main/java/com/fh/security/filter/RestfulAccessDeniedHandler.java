package com.fh.security.filter;

import cn.hutool.json.JSONUtil;
import com.fh.utils.CommonsReturn;
import com.fh.utils.ReturnCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestfulAccessDeniedHandler
 * @Deacription 当访问接口没有权限时，自定义的返回结果
 * @Author 路东生
 * @Date 2020/12/21 11:24
 **/
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //返回的文字编码
        response.setCharacterEncoding("UTF-8");
        //返回的类型
        response.setContentType("application/json");
        //设置返回的值
        response.getWriter().println(JSONUtil.parse(CommonsReturn.error(ReturnCode.NO_PERMISSION)));
        //进行清除
        response.getWriter().flush();
    }
}
