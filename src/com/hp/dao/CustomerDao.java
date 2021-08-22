package com.hp.dao;

import com.hp.bean.Customer;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDao{

    //查询两表
    public List<Map> query(Map map) {
        List list=new ArrayList();
        //开连接
        String Page=(String) map.get("page");
        String limit=(String) map.get("limit");


        String uname=(String) map.get("uname");
        String cname=(String) map.get("cname");
        String lookTime=(String) map.get("lookTime");
        String sex=(String) map.get("sex");
        String phoneNumber=(String) map.get("phoneNumber");



        Connection connection=null;


        connection = DBHelper.getConnection();
        String sql="select c.* , t.username as username , t.password as password ,  t.real_name as real_name , t.type as type   from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";

        sql=sql +" and t.is_del=1  ";

        if(null!=cname&&cname.length()>0){
            sql=sql+"and  c.cust_name like '%"+cname+"%'  ";
        }
        if(null!=lookTime&&lookTime.length()>0){
            sql=sql+"and  c.modify_time = "+lookTime+"  ";
        }
        if(null!=phoneNumber&&phoneNumber.length()>0){
            sql=sql+"and  c.cust_phone = "+phoneNumber+" ";
        }
        if(null!=sex&&sex.length()>0){
            sql=sql+"and  c.cust_sex = "+sex+"  ";
        }
        if(null!=uname&&uname.length()>0){
            sql=sql+"and  t.username like '%"+uname+"%'  ";
        }
        sql= sql+"limit ?, ?   ";
        System.out.println("sql = " + sql);

        PreparedStatement ps=null;
        ResultSet rs=null;
        PageBeanUtil pageBeanUtil=new PageBeanUtil(Integer.parseInt(Page),Integer.parseInt(limit));

        try {
            ps=connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());
            ps.setInt(2,Integer.parseInt(limit));

            rs=ps.executeQuery();

            while (rs.next()){
                Map dataMap=new HashMap();
                dataMap.put("id",rs.getInt("id"));
                dataMap.put("cust_name",rs.getString("cust_name"));
                dataMap.put("cust_company",rs.getString("cust_company"));
                dataMap.put("cust_position",rs.getString("cust_position"));

                dataMap.put("cust_phone",rs.getString("cust_phone"));
                dataMap.put("cust_birth",rs.getString("cust_birth"));
                dataMap.put("cust_sex",rs.getInt("cust_sex"));
                dataMap.put("user_id",rs.getInt("user_id"));
                dataMap.put("create_time",rs.getString("create_time"));
                dataMap.put("modify_time",rs.getString("modify_time"));
                dataMap.put("username",rs.getString("username"));
                dataMap.put("password",rs.getString("password"));
                dataMap.put("real_name",rs.getString("real_name"));
                dataMap.put("type",rs.getInt("type"));
                list.add(dataMap);

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

        return list;

    }
public int selectAllByParamCount(Map map){

        Connection connection=DBHelper.getConnection();
       // String sql="select count(*) total  from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1";
        String sql="select  count(*) total  from t_customer c  join t_user  t  on c.user_id  = t.id  where 1=1 ";
        int total=0;
         PreparedStatement ps=null;
        ResultSet rs=null;
        try {
        ps= connection.prepareStatement(sql);
       rs=ps.executeQuery();
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
//新增
    public int addCustomer(Customer customer){
        int i=0;
        Connection connection = DBHelper.getConnection();
        String sql="insert into t_customer values (null,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps =null;

        try {
             ps = connection.prepareStatement(sql);
            ps.setString(1,customer.getCust_name());
            ps.setString(2,customer.getCust_company());
            ps.setString(3,customer.getCust_position());
            ps.setString(4,customer.getCust_phone());
            ps.setString(5,customer.getCust_birth());
            ps.setInt(6,customer.getCust_sex());
            ps.setString(7,customer.getCust_desc());
            ps.setInt(8,customer.getUser_id());
            ps.setString(9,customer.getCreate_time());
            ps.setString(10,customer.getModify_time());


            i = ps.executeUpdate();

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
    //删除
    public int delCustomer(int id){
        Connection connection = DBHelper.getConnection();
        String sql="delete from t_customer where id=?";
        PreparedStatement ps=null;
        int i=0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
             i = ps.executeUpdate();
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

    public static void main(String[] args) {
        //测试
        Map paramMap=new HashMap();
        paramMap.put("page","1");
        paramMap.put("limit","5");
        paramMap.put("cname","李小花");
        CustomerDao customerDao=new CustomerDao();
        List<Map> query = customerDao.query(paramMap);
        System.out.println("query = " + query);
        System.out.println("query.size() = " + query.size());

    /*  Map paramMap=new HashMap();
        CustomerDao customerDao=new CustomerDao();
        int i = customerDao.selectAllByParamCount(paramMap);
        System.out.println("i = " + i);*/

/*
CustomerDao dao=new CustomerDao();
        Customer customer=new Customer();

        customer.setCust_name("这是dao的测试");
        customer.setCust_company("这是dao的测试");
        customer.setCust_position("这是dao的测试");
        customer.setCust_phone("1673674567");
        customer.setCust_birth("这是dao的测试");
        customer.setCust_sex(2);
        customer.setCust_desc("这是dao的测试");
        customer.setUser_id(7);
        customer.setCreate_time("2019");
        customer.setModify_time("2018");
        int i = dao.addCustomer(customer);
        System.out.println("i = " + i);
*/
/*CustomerDao dao=new CustomerDao();
        int i = dao.delCustomer(26);
        System.out.println("i = " + i);*/

    }
}