package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.bean.Visit;
import com.hp.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddVisitServlet",urlPatterns = "/AddVisitServlet")
public class AddVisitServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        String cust_id = req.getParameter("cust_id");

        String user_id = req.getParameter("user_id");
        String visit_desc = req.getParameter("visit_desc");
        String visit_time = req.getParameter("visit_time");
        System.out.println("visit = " + visit_time);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String create_time = simpleDateFormat.format(new Date());

        Visit visit=new Visit();

        visit.setCust_id(Integer.parseInt(cust_id));
        visit.setUser_id(Integer.parseInt(user_id));
        visit.setVisit_desc(visit_desc);
        visit.setVisit_time(visit_time);
        visit.setCreate_time(create_time);


        Map map=new HashMap();

        VisitService service= new VisitService();
        map=service.addVisitService(visit);

        String s= JSONObject.toJSONString(map);


        PrintWriter writer=resp.getWriter();


        writer.print(s);
        writer.close();

    }
}
