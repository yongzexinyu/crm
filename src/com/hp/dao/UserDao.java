package com.hp.dao;

import com.hp.bean.User;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//dao层应该是个接口，为什么是因为可以使用AOP,目前不用写成aop就可以直接写成类了
public class UserDao {
    //增删改查
    //查询 select * from t_user
    //dao层 如何 和数据库做对接,我们用的知识点叫JDBC,很基础的一个必须的技术
    //很多框架都是基于jDBC来的，所以必须学习
    //要连接数据库，就需要用DBHelper.connection()来创建一个和mysql的一个连接的对象
    //这个对象可以负责和mysql连接
public List<User> selectAll(){
    List<User> users=new ArrayList<>();
    Connection connection= DBHelper.getConnection();
    String sql="select * from t_user";
    PreparedStatement ps=null;
    ResultSet rs=null;
    try {
        //3.使用连接对象，获取预编译对象
        ps=connection.prepareStatement(sql);
        //4.预编译对象，得到结果集
        rs=ps.executeQuery();
        //5.遍历结果，一一的取对象
        while(rs.next()) {
           // System.out.println("username=" + rs.getString("username"));//拿到每个row
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setCreate_time(rs.getString("create_time"));
            user.setImg(rs.getString("img"));
            user.setIs_del(rs.getInt("is_del"));
            user.setModify_time(rs.getString("modify_time"));
            user.setPassword(rs.getString("password"));
            user.setReal_name(rs.getString("real_name"));
            user.setType(rs.getInt("type"));
            user.setUsername(rs.getString("username"));
            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   return users;

    }
public int addUser(User user){
    //1.创建连接对象
    Connection connection=DBHelper.getConnection();
    //2.sql语句 因为添加的数据是变量，所以要用问号代替
    PreparedStatement ps=null;
    int i=0;
    String sql="insert into t_user values(null,?,?,?,?,?,?,?,?)";
    try {
     //3.预编译 sql
     ps=  connection.prepareStatement(sql);
     ps.setString(1,user.getUsername());
     ps.setString(2,user.getPassword());
     ps.setString(3,user.getReal_name());
     ps.setString(4,user.getImg());
     ps.setInt(5,user.getType());
     ps.setInt(6,user.getIs_del());
     ps.setString(7,user.getCreate_time());
     ps.setString(8,user.getModify_time());
     //4.执行预编译对象
    i= ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
return  i;
}
public int updateUser(User user){
    Connection connection=DBHelper.getConnection();
    String sql="update t_user set username=?,type=?,real_name=?,password=?,modify_time=?,is_del=?,img=?,create_time=? where id=?";
    PreparedStatement ps=null;
    int i=0;
    try {
        ps=connection.prepareStatement(sql);
        ps.setString(1,user.getUsername());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getReal_name());
        ps.setString(4,user.getImg());
        ps.setInt(5,user.getType());
        ps.setInt(6,user.getIs_del());
        ps.setString(7,user.getCreate_time());
        ps.setString(8,user.getModify_time());
        ps.setInt(9,user.getId());
       i= ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return i;
}
public int delUser(int id){
    Connection connection=DBHelper.getConnection();
    String sql="delete from t_user where id=?";
    PreparedStatement ps=null;
    int i=0;
    try {
       ps= connection.prepareStatement(sql);
       ps.setInt(1,id);
       i=ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return  i;
}
//登录 select * from t_user where username=? and password=?
User user=null;
public User login(String username, String password) {

    //1.创建连接
    Connection connection = DBHelper.getConnection();
    //2.sql语句
    String sql="select * from t_user where username=? and password=?";
    PreparedStatement ps=null;
    ResultSet rs=null;
    try {
        //3.使用连接对象获取预编译对象
        ps= connection.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,password);
        rs = ps.executeQuery();
      if(rs.next()){
          user=new User();
          System.out.println("登录成功");
          user.setUsername(rs.getString(username));
          user.setType(rs.getInt("type"));
          user.setReal_name(rs.getString("real_name"));
          user.setPassword(rs.getString("password"));
          user.setModify_time(rs.getString("modify_time"));
          user.setIs_del(rs.getInt("is_del"));
          user.setImg(rs.getString("img"));
          user.setCreate_time(rs.getString("cteate_time"));
      }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return user;
    }
    //m是页数
    //n是条数
public List<User> selectAllByParam(int page,int limit){
    List<User> lists=new ArrayList<>();
    //1.开链接
    Connection connection=DBHelper.getConnection();
    //2.sql语句
    String sql="select * from t_user limit ?,?";
    //3.编译sql
    PreparedStatement ps=null;
    ResultSet rs=null;
    PageBeanUtil pageBeanUtil=new PageBeanUtil(page,limit);//因为第一个问号需要求出来
    try {
        ps = connection.prepareStatement(sql);
        ps.setInt(1,pageBeanUtil.getStart());
        ps.setInt(2,limit);
        //4.执行sql
       rs = ps.executeQuery();
        while(rs.next()) {
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setCreate_time(rs.getString("create_time"));
            user.setImg(rs.getString("img"));
            user.setIs_del(rs.getInt("is_del"));
            user.setModify_time(rs.getString("modify_time"));
            user.setPassword(rs.getString("password"));
            user.setReal_name(rs.getString("real_name"));
            user.setType(rs.getInt("type"));
            user.setUsername(rs.getString("username"));
            lists.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return lists;
}
//查询总条数
    public int selectCount(){
    //1.开链接
        Connection connection=DBHelper.getConnection();
        //2.sql
        String sql="select count(*) total from t_user";
        PreparedStatement ps =null;
        ResultSet rs =null;
        int total=0;
        //3.编译sql
        try {
            ps = connection.prepareStatement(sql);
            //执行
            rs = ps.executeQuery();
            if(rs.next()){
                total=rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  total;
    }
    public static void main(String[] args) {
        UserDao dao=new UserDao();
       /* List<User> users= dao.selectAll();                   全查
        for (User user:users) {
            System.out.println("user = " + user);
        }*/
     /*  User user=new User();                                 添加
       user.setUsername("汤姆");
       user.setType(1);
       user.setReal_name("Tom");
       user.setPassword("123");
       user.setModify_time("2012-5-20");
       user.setIs_del(1);
       user.setImg("xxx");
       user.setCreate_time("2012-5-20");

         int i=  dao.addUser(user);
        System.out.println("i = " + i);*/
     /*  User user=new User();                                 修改
       user.setUsername("马可");
       user.setType(1);
       user.setReal_name("make");
       user.setPassword("123");
       user.setModify_time("2018-5-20");
       user.setIs_del(1);
       user.setImg("xxx");
       user.setCreate_time("2018-5-20");
        int i=  dao.updateUser(user);
        System.out.println("i = " + i);*/
    /* int i=dao.delUser(61);                             //删除
        System.out.println("i = " + i);*/
      /*  User abc = dao.login("abc", "123456");
        System.out.println("abc = " + abc);*/

    /*    List<User> userList = dao.selectAllByParam(2, 5);     //分页查询的测试
        System.out.println("userList = " + userList);
        System.out.println("userList = " + userList.size());*/
        int i = dao.selectCount();
        System.out.println("i = " + i);
    }
}
