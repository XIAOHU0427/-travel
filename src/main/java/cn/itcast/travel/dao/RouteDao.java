package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

public interface RouteDao {
    int findTotalCount(Map map);
    List<Route> findByPage(Map map);

    Route findOneById(Integer rid);
}
