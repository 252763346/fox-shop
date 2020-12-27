package com.fh.security.filter;

import cn.hutool.json.JSONUtil;
import com.fh.utils.CommonsReturn;
import com.fh.utils.ReturnCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RestAuthenticationEntryPoint
 * @Deacription 当未登录或者token失效访问接口时，自定义的返回结果
 * @Author 路东生
 * @Date 2020/12/21 11:23
 **/
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //返回的文字编码
        response.setCharacterEncoding("UTF-8");
        //返回的类型
        response.setContentType("application/json");
        //设置返回的值
        response.getWriter().println(JSONUtil.parse(CommonsReturn.error(ReturnCode.LOGIN_DISABLED)));
        //进行清除
        response.getWriter().flush();
    }
}
