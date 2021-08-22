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

@WebServlet(name = "DelCustomerServlet",urlPatterns = "/DelCustomerServlet")
public class DelCustomerServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        //接受前端传过来的参数
        //重点:servlet收取数组
        String[] parameterValues = req.getParameterValues("ids[]");
        System.out.println("parameterValues = " + parameterValues);
        CustomerService service=new CustomerService();

        for (String idStr:parameterValues) {
            int i = service.delCustomerService(Integer.parseInt(idStr));
            System.out.println("i = " + i);
        }
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","ok");
        String s= JSONObject.toJSONString(map);
        PrintWriter writer=resp.getWriter();
        writer.print(s);
        writer.close();
    }
}
