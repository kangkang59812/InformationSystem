package com.sm.global;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    // 定义编码方式 默认为utf-8
    private String encoding="UTF-8";
    public void init(FilterConfig filterConfig) throws ServletException {
        //读取web.xml中Filter配置的初始化參数
        if(filterConfig.getInitParameter("ENCODING")!=null)
            encoding =filterConfig.getInitParameter("ENCODING");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);//设置request字符编码
        servletResponse.setCharacterEncoding(encoding);
        //转到下一个过滤器
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {
        encoding=null;
    }
}
