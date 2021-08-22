package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DelCustomerOneServlet",urlPatterns = "/DelCustomerOneServlet")
public class DelCustomerOneServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        //接受前端传过来的参数
        String id = req.getParameter("id");
        System.out.println("id = " + id);
        CustomerService service=new CustomerService();
        int i = service.delCustomerService(Integer.parseInt(id));

        Map map=new HashMap<>();
        if(i==1){
            map.put("code",0);
            map.put("msg","ok");
        }
        String s= JSONObject.toJSONString(map);
        PrintWriter writer=resp.getWriter();
        writer.print(s);
        writer.close();
    }
}
