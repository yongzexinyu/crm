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

@WebServlet(name = "EditServlet",urlPatterns = "/EditServlet")
public class EditServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*super.service(req, resp);*/
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //2.接受前端过来的参数


        String username=req.getParameter("username");
        System.out.println("username = " + username);
        String password=req.getParameter("password");
        System.out.println("password = " + password);
        String type=req.getParameter("type");
        System.out.println("type = " + type);
        String is_del=req.getParameter("is_del");
        if(is_del.equals("是")){
            is_del="1";
        }if(is_del.equals("否")){
            is_del="2";
        }
        System.out.println("is_del = " + is_del);
        String modify_time =req.getParameter("modify_time");
        System.out.println("modify_time = " + modify_time);
        String real_name=req.getParameter("real_name");
        System.out.println("real_name = " + real_name);
        String id=req.getParameter("id");
        System.out.println("id = " + id);

        UserService userService=new UserService();
        Map map = userService.selectById(Integer.parseInt(id));
        User data= (User) map.get("data");
        User user=new User();
        user.setModify_time(modify_time);
        user.setCreate_time(data.getCreate_time());
        user.setIs_del(Integer.parseInt(is_del));
        user.setType(Integer.parseInt(type));
        user.setReal_name(real_name);
        user.setImg(data.getImg());
        user.setPassword(password);
        user.setUsername(username);
        user.setId(Integer.parseInt(id));



       Map map1= userService.editUser(user);
        System.out.println("map = " + map1);
        String jsonString = JSON.toJSONString(map1);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonString);
        writer.close();
    }
}
