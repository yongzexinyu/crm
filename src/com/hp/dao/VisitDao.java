package com.hp.dao;

import com.hp.bean.Visit;
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

public class VisitDao {
    //拜访记录
    public int addVisit(Visit visit){
        Connection connection= DBHelper.getConnection();
        String sql="insert into t_visit values (null,?,?,?,?,?)";
        PreparedStatement ps =null;
        int i =0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());

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
    //全查
    public List<Map> selectAllVisit(Map map){
        List visits=new ArrayList<>();
        String page= (String) map.get("page");
        String limit=(String)map.get("limit");

      String cust_name = (String) map.get("cust_name");

      String cust_phone = (String) map.get("cust_phone");

      String cust_birth = (String) map.get("cust_birth");

      String cust_position = (String) map.get("cust_position");

      String cust_company = (String) map.get("cust_company");

      String real_name = (String) map.get("real_name");



        Connection connection=DBHelper.getConnection();
        String sql="select v.* ,c.cust_name,c.cust_company,c.cust_position,c.cust_phone,c.cust_birth,u.real_name  from t_visit v join t_user u on  v.user_id=u.id join t_customer c on v.cust_id=c.id where 1=1";
        sql=sql+ " and u.is_del=1 ";


        if(null!=cust_name&&cust_name.length()>0){
            sql=sql+"and  c.cust_name like '%"+cust_name+"%'  ";
        }
        if(null!=cust_position&&cust_position.length()>0){
            sql=sql+"and  c.cust_position = "+cust_position+"  ";
        }
        if(null!=cust_birth&&cust_birth.length()>0){
            sql=sql+"and  c.cust_birth = "+cust_birth+" ";
        }
        if(null!=cust_company&&cust_company.length()>0){
            sql=sql+"and  c.cust_company = "+cust_company+"  ";
        }
        if(null!=cust_phone&&cust_phone.length()>0){
            sql=sql+"and  c.cust_phone = "+cust_phone+"  ";
        }
        if(null!=real_name&&real_name.length()>0){
            sql=sql+"and  u.real_name like '%"+real_name+"%'  ";
        }

        sql=sql+ " limit ? , ? ";
        PageBeanUtil pageBeanUtil=new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));//因为第一个问号需要求出来
        PreparedStatement ps =null;
        ResultSet rs =null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());
            ps.setInt(2,Integer.parseInt(limit));
            rs = ps.executeQuery();
            while(rs.next()){
               Map map1=new HashMap();
                map1.put("id",rs.getInt("id"));
                map1.put("user_id",rs.getInt("user_id"));
                map1.put("cust_id",rs.getInt("cust_id"));
                map1.put("visit_desc",rs.getString("visit_desc"));
                map1.put("visit_time",rs.getString("visit_time"));
                map1.put("create_time",rs.getString("create_time"));
                map1.put("cust_name",rs.getString("cust_name"));
                map1.put("cust_company",rs.getString("cust_company"));
                map1.put("cust_position",rs.getString("cust_position"));
                map1.put("cust_phone",rs.getString("cust_phone"));
                map1.put("cust_birth",rs.getString("cust_birth"));
                map1.put("real_name",rs.getString("real_name"));
                visits.add(map1);
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
        return visits;
    }
    //查询总条数
    public int selectVisitCount(Map map){
        Connection connection=DBHelper.getConnection();
        String sql="select count(*) total from t_visit v join t_user u on  v.user_id=u.id join t_customer c on v.cust_id=c.id where 1=1";
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

    public static void main(String[] args) {

      /*  Visit visit=new Visit();      添加
        visit.setUser_id(7);
        visit.setCust_id(7);
        visit.setVisit_desc("到此一游");
        visit.setVisit_time("2021-2-12");
        visit.setCreate_time("2021-2-12");
        int i = dao.addVisit(visit);
        System.out.println("i = " + i);*/
        Map paramMap=new HashMap();
        VisitDao dao=new VisitDao();
       paramMap.put("page","1");    //全查
        paramMap.put("limit","5");
        paramMap.put("cust_company","腾讯");
        List<Map> visits = dao.selectAllVisit(paramMap);
        System.out.println("visits = " + visits);
        System.out.println("visits.size() = " + visits.size());

        /*int i = dao.selectVisitCount(paramMap);
        System.out.println("i = " + i);*/
    }
}
