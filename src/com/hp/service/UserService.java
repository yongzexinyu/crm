package com.hp.service;

import com.hp.bean.User;
import com.hp.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    //登录
    public Map login(String username, String password, HttpServletRequest request){
        Map map= new HashMap<>();
        //登录
        // service 层要调用dao层
        UserDao dao =new UserDao();
        User userFromDB=dao.login(username,password);
        if(null == userFromDB){
            //没查不出，即:账户或者密码不正确
            map.put("code",4001);
            map.put("msg","账户名或者密码不正确");
            return  map;
        }else{
            HttpSession session =request.getSession();
            session.setAttribute("user",userFromDB);
            map.put("code",0);
            map.put("msg","登录成功");
            return  map;
        }
    }
    //带参数的分页查询
    public Map selectAllByParam(int page,int limit){
        UserDao userDao=new UserDao();
        List<User> userList = userDao.selectAllByParam(page, limit);
        int i=userDao.selectCount();
        Map map=new HashMap();
        //map.put("code",0);
        map.put("code11",200);
        map.put("msg11","查询成功");
        map.put("count11",i);//把死的写活
        map.put("data11",userList);
    //根据layui返回的json格式去封装你的数据，如果不一样，需要layui 解析
        /*
        * {
        * code:0,
        * msg:"",
        *count:100,
        * data:[每条数据]
        * }
        * */
       /* return map;*/
        //错误示例
        Map  map1=new HashMap();
        map1.put("number",2001);
        map1.put("message","数据查询成功");
        map1.put("object",map);
        return  map1;
    }
}
