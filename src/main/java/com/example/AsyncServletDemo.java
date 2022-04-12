package com.example;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;


@WebServlet(urlPatterns = "/asyncServlet", asyncSupported = true)
public class AsyncServletDemo extends HttpServlet {

    ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    @Override
    protected void service(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {


        // 3.0的异步servlet
        final AsyncContext asyncContext = req.startAsync(req, resp);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    asyncContext.getResponse().getWriter().print("async result"); //
                    asyncContext.complete();
                    System.out.println("已经掉了complete方法了");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


//        ServletInputStream inputStream = req.getInputStream();
//        inputStream.setReadListener(new ReadListener() {
//            @Override
//            public void onDataAvailable() throws IOException {
//                // du
//            }
//            @Override
//            public void onAllDataRead() throws IOException {
//
//            }
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//        });
//
//        ServletOutputStream outputStream = resp.getOutputStream();
//        outputStream.setWriteListener(new WriteListener() {
//            @Override
//            public void onWritePossible() throws IOException {
//                //
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//        });
    }

}
