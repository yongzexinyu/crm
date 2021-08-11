package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*super.service(req, resp);*/
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //2.接受前端过来的2个参数 page limit
        String page=req.getParameter("page");
        String limit=req.getParameter("limit");
        //3.调用Service
        UserService userService=new UserService();
        Map map=userService.selectAllByParam(Integer.parseInt(page),Integer.parseInt(limit));
        //4.把map变为json
        String s= JSONObject.toJSONString(map);
        //5.使用流输出
        PrintWriter writer = resp.getWriter();
        writer.print(s);
        writer.close();

    }
}
