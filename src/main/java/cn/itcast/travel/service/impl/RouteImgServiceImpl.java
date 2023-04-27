package cn.itcast.travel.service.impl;


import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.service.RouteImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteImgServiceImpl implements RouteImgService {
    @Autowired
    private RouteImgDao routeImgDao;

    @Override
    public List<RouteImg> listRouteImg(Integer rid){
        return routeImgDao.listRouteImg(rid);
    }
}
