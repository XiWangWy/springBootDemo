package com.bless.Security;

import com.alibaba.fastjson.JSONObject;
import com.bless.Entity.JSONResult;
import com.bless.Utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if(response.getStatus() != 200){
            log.info("错误状态码：" + response.getStatus());
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String reason = "错误原因：" + authException.getMessage();
        JSONResult result = ResultUtil.error(HttpServletResponse.SC_FORBIDDEN,reason);
        response.getWriter().write(JSONObject.toJSONString(result,true));
    }
}