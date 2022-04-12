package com.example;

import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo extends HttpServlet implements SingleThreadModel{
     // Request imple HttpServletRequest

    //  servletDemo.doGet(new RequestFacade(),  new ResponseFacade())  Tomcat

    // Request、 RequestFacade  // 门面

    //  Request对象 ， Tomcat   new Reqeust();   ?
    // 假设 ： 。。。。。。。 ---> servletDemo.doGet(new RequestFacade(),  new ResponseFacade())


    // servletDemo
    // 共享servletDemo--->线程不安全

    // 一个请求--对应servletDemo
    // 100qingq  servletDemo
    // 20servletDemo   21--->阻塞



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        req.getInputStream(); // body

//        resp.getOutputStream().print("123");  // socket

        resp.getWriter().println("hello servlet");
    }
}
