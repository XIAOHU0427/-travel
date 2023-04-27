package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.WeatherInfo;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.utils.HttpURLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;


    @Override
    public PageBean<Route> pageQuery(String cid, int userCurrentPage, int pageSize) {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(userCurrentPage);
        pageBean.setPageSize(pageSize);

        Map map = new HashMap();
        map.put("cid",cid);
         int totalCount =  routeDao.findTotalCount(map);
         if(totalCount==0){
             pageBean.setTotalPage(0);
             pageBean.setTotalCount(0);//一共有多少数据
             pageBean.setList(new ArrayList<>());//当前页显示的数据

             return pageBean;
         }
         //不为空findTotalCount
         pageBean.setTotalCount(totalCount);//设置一共有多少数据
        //设置总页数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pageBean.setTotalPage(totalPage);
//        进行分页查询
        int start = (userCurrentPage-1)*pageSize;
        map.put("start",start);
        map.put("pageSize",pageSize);
        List<Route> list =  routeDao.findByPage(map);
        pageBean.setList(list);//设置分页数据


        return pageBean;
    }
  @Override
    public  Route findOneById(Integer rid){
        return  routeDao.findOneById(rid);
  }

    @Override
    public WeatherInfo getWeatherInfo(Integer districtid) {
        String url="https://api.map.baidu.com/weather/v1/?district_id="+districtid+"&data_type=all&ak=s3Bvo9OnQuGqcL0rVUUqwh9bNGgVilhQ";
        String s = HttpURLUtil.doGet(url);
        JSONObject jsonObject = JSON.parseObject(s, JSONObject.class);
        if(jsonObject.getInteger("status")==0&& jsonObject.getInteger("status")!=null){
            JSONObject result = jsonObject.getJSONObject("result");
            JSONObject now = result. getJSONObject("now");
            JSONArray forecasts = result.getJSONArray("forecasts");
            return  new WeatherInfo(now,forecasts);
        }
        return null;
    }
}
