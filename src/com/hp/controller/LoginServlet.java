package com.hp.controller;

import com.alibaba.fastjson.JSON;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*super.service(req, resp);*/
        //要接受 登录传过来的3个参数
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //2.接受前端过来的3个参数
        String username = req.getParameter("username");
        String userpwd= req.getParameter("userpwd");
        String code= req.getParameter("code");
        //3.第一次登录，登陆的时候首先要验 证验证码是否正确
        //3.1获取后台的验证码
        HttpSession session = req.getSession();
        String codeFromSession = (String) session.getAttribute("code");
        if(!codeFromSession.equals(code)){
            //验证码错误
            //向前端输入一段json 告知前端验证码错误了
            PrintWriter writer = resp.getWriter();
            Map map=new HashMap<>();
            map.put("code",400);
            map.put("msg","验证码不正确");
            String jsonString = JSON.toJSONString(map);
            writer.print(jsonString);
            writer.close();
        }else{
            //验证码正确 继续判断 账号和 密码
            System.out.println("验证码正确，该判断 账号 和 密码了");
            //就需要service/dao 层判断，如果咱们业务不是特别的多，那么
            //可以直接 不用service 层
            UserService service=new UserService();
            Map map = service.login(username, userpwd, req);
            String jsonString = JSON.toJSONString(map);
            PrintWriter writer = resp.getWriter();
            writer.print(jsonString);
            writer.close();
        }
    }
}
