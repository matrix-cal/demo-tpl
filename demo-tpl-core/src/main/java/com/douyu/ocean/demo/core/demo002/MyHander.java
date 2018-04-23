package com.douyu.ocean.demo.core.demo002;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * MyHander
 *
 * @author weiqi
 * @create 2018-04-21 15:44:00
 */
public class MyHander implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream is = httpExchange.getRequestBody();
        String response = "<h1>hello jdk6 http server</h1>";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes("UTF-8"));
        os.close();
    }
}
