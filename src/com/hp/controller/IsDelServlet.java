package com.hp.controller;

import com.alibaba.fastjson.JSON;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "IsDelServlet",urlPatterns = "/IsDelServlet")
public class IsDelServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* super.service(req, resp);*/
        //要接受 前端传过来的参数
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");


        String del = req.getParameter("sDel");
        String id=req.getParameter("id");
        

        System.out.println("del = " + del);
        System.out.println("id = " + id);
        UserService userService=new UserService();

       // int i=7;
        Map map= userService.update(Integer.parseInt(del), Integer.parseInt(id) );
        System.out.println("map = " + map);
        String jsonString = JSON.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonString);
        writer.close();
    }
}
