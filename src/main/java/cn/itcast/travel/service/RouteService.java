package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.WeatherInfo;

public interface RouteService {
//    查询线路分页数据
    PageBean<Route> pageQuery(String cid, int userCurrentPage, int pageSize);

    Route findOneById(Integer rid);

    WeatherInfo getWeatherInfo(Integer districtid);
}
