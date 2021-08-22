package com.hp.service;

import com.hp.bean.Customer;
import com.hp.dao.CustomerDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    public Map selectAllByParam(Map map){
        CustomerDao customerDao=new CustomerDao();
        List<Map> maps=customerDao.query(map);
        Map codeMap=new HashMap();
        codeMap.put("code",0);
        codeMap.put("data",maps);
        codeMap.put("msg","ok");
        Map countMap=selectAllByParamCount(map);
        int count=(int)countMap.get("data");
        codeMap.put("count",count);
        return  codeMap;
    }
    //全查总条数
    public Map selectAllByParamCount(Map map){
        Map codeMap=new HashMap();
        CustomerDao dao=new CustomerDao();
        int i=dao.selectAllByParamCount(map);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","ok");
        return  codeMap;

    }
    //新增
    public Map addCustomerService(Customer customer){
        CustomerDao dao=new CustomerDao();
        int i = dao.addCustomer(customer);
        Map map=new HashMap();
        if(i==1){
            map.put("code",0);
            map.put("msg","添加成功");

        }else{

            map.put("code",400);
            map.put("msg","添加失败");
        }
       return  map;
    }
    //删除
    public int delCustomerService(Integer id){
        CustomerDao dao=new CustomerDao();
        int i = dao.delCustomer(id);
        return  i;
    }


}
