package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SelectAllVisitServlet",urlPatterns = "/SelectAllVisitServlet")
public class SelectAllVisitServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        //接受前端传过来的参数
        String page= req.getParameter("page");
        String limit= req.getParameter("limit");


        String cust_name = req.getParameter("cust_name");
                 System.out.println("cust_name = " + cust_name);
        String cust_birth = req.getParameter("cust_birth");
                 System.out.println("cust_birth = " + cust_birth);
        String cust_company = req.getParameter("cust_company");
                  System.out.println("cust_company = " + cust_company);
        String cust_phone = req.getParameter("cust_phone");
                 System.out.println("cust_phone = " + cust_phone);
        String cust_position = req.getParameter("cust_position");
                   System.out.println("cust_position = " + cust_position);
        String real_name = req.getParameter("real_name");
                  System.out.println("real_name = " + real_name);

        Map map1=new HashMap();
        map1.put("page",page);
        map1.put("limit",limit);

        map1.put("cust_name",cust_name);
        map1.put("cust_phone",cust_phone);
        map1.put("cust_position",cust_phone);
        map1.put("cust_company",cust_company);
        map1.put("real_name",real_name);
        map1.put("cust_birth",cust_birth);

        VisitService service=new VisitService();
        Map map = service.selectAllVisitService(map1);
        String s= JSONObject.toJSONString(map);
        //5.使用流输出
        PrintWriter writer = resp.getWriter();
        writer.print(s);
        writer.close();



    }
}
