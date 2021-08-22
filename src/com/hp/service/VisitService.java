package com.hp.service;

import com.hp.bean.Visit;
import com.hp.dao.VisitDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitService {
    //拜访记录
    public Map addVisitService(Visit visit){
        VisitDao dao=new VisitDao();
        int i = dao.addVisit(visit);
        Map map=new HashMap();
        if(i>0){
            map.put("code",0);
            map.put("msg","添加成功");
        }else{
            map.put("code",400);
            map.put("msg","添加失败");
        }
        return  map;
    }
    //全查
    public Map selectAllVisitService(Map map2){
        VisitDao dao=new VisitDao();
        List<Map> visits = dao.selectAllVisit(map2);
        Map map=new HashMap();
            map.put("code",0);
            map.put("data",visits);
            map.put("msg","ok");
        Map map1 = selectVisitCount(map2);
        int count = (int)map1.get("data");
        map.put("count",count);
        return map;
    }
    //查询总条数
    public Map selectVisitCount(Map map1){
        VisitDao dao=new VisitDao();
        Map map=new HashMap();
        int i= dao.selectVisitCount(map1);

        map.put("code",0);
        map.put("data",i);
        map.put("msg","ok");
        
        return map;
    }
}
