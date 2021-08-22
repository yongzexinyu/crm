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

@WebServlet(name = "CustomerSelectAllServlet",urlPatterns = "/CustomerSelectAllServlet")
public class CustomerSelectAllServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*super.service(req, resp);*/
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //2.接受前端过来的参数 limit page
        String page= req.getParameter("page");
        String limit= req.getParameter("limit");
        
        String uname=req.getParameter("uname");
        System.out.println("uname = " + uname);
        String cname=req.getParameter("cname");
        System.out.println("cname = " + cname);
        String phoneNumber=req.getParameter("phoneNumber");
        System.out.println("phoneNumber = " + phoneNumber);
        String lookTime=req.getParameter("lookTime");
        System.out.println("lookTime = " + lookTime);
        String sex=req.getParameter("sex");
        System.out.println("sex = " + sex);
        
        Map paramMap=new HashMap<>();
        paramMap.put("page",page);
        paramMap.put("limit",limit);
        paramMap.put("cname",cname);
        paramMap.put("lookTime",lookTime);
        paramMap.put("uname",uname);
        paramMap.put("sex",sex);
        paramMap.put("phoneNumber",phoneNumber);

        CustomerService customerService=new CustomerService();
        Map map=customerService.selectAllByParam(paramMap);
        String jsonString = JSONObject.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonString);
        writer.close();

    }
}
