package com.sm.global;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        String path=request.getServletPath();
        if(path.toLowerCase().indexOf("login")!=-1){
            filterChain.doFilter(request, response);
        }else {
            //要经过登录才能访问的页面
            HttpSession session=request.getSession();
            Object obj=session.getAttribute("USER");
            if(obj!=null){
                filterChain.doFilter(request, response);
            }
            else {
                //可能是从不同目录过来的
                response.sendRedirect(request.getContextPath()+"/toLogin.do");
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
