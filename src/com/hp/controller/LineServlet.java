package com.hp.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LineServlet",urlPatterns = "/LineServlet")
public class LineServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //2.拿到验证码
        String code=lineCaptcha.getCode();
        //3.获取session
        HttpSession session= req.getSession();
        //4.把验证码放到session中
        session.setAttribute("code",code);
        //将验证码发送到浏览器
        ServletOutputStream outputStream=resp.getOutputStream();
        lineCaptcha.write(outputStream);
        outputStream.close();
    }
}
