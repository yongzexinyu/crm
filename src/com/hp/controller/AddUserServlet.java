package com.hp.controller;

import com.alibaba.fastjson.JSON;
import com.hp.bean.User;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "AddUserServlet",urlPatterns = "/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*super.service(req, resp);*/
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //2.接受前端过来的参数
        String username = req.getParameter("username");
        System.out.println("username = " + username);      
        String password=req.getParameter("password");
        System.out.println("password = " + password);
        String real_name=req.getParameter("real_name");
        System.out.println("real_name = " + real_name);
        String img=req.getParameter("img");
        System.out.println("img = " + img);
        String type=req.getParameter("type");
        System.out.println("type = " + type);
        String del=req.getParameter("sdel");
        if(del.equals("是")){
            del="1";
        }if(del.equals("否")){
            del="2";
        }
        System.out.println("del = " + del);
        String create_time=req.getParameter("create_time");
        System.out.println("create_time = " + create_time);
        String modify_time=req.getParameter("modify_time");
        System.out.println("modify_time = " + modify_time);


       User user=new User();
       user.setUsername(username);
       user.setPassword(password);
       user.setReal_name(real_name);
       user.setImg(img);
       user.setType(Integer.parseInt(type));
       user.setIs_del(Integer.parseInt(del));
       user.setCreate_time(create_time);
       user.setModify_time(modify_time);


        UserService userService=new UserService();
        Map map = userService.add(user);
        System.out.println("map = " + map);
        String jsonString = JSON.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonString);
        writer.close();

    }
}
