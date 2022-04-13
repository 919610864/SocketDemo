package com.example.filter;

import javax.servlet.*;
import java.io.IOException;

public class FilterDemo implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

       @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // ReqeustFacade

        System.out.println("doFilter之前");
        filterChain.doFilter(servletRequest, servletResponse); // servelt.doget
        System.out.println("doFilter之后");
    }

    @Override
    public void destroy() {

    }
}
