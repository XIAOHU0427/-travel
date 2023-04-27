package cn.itcast.travel.controller;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteImgService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.SellerService;
import com.aliyuncs.utils.StringUtils;
import javafx.scene.shape.LineTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;
    @Autowired
    private RouteImgService routeImgService;
    @Autowired
    private SellerService sellerService;


    @ResponseBody
    @GetMapping("/pageQuery")
    public PageBean<Route> pageQuery(String cid,String currentPage,String size){
//        接收数据对数据进行处理
//        查询数据时的当前页
        int userCurrentPage = 1;
        if(!StringUtils.isEmpty(currentPage)){
//            当前页不为null到时候进入if中
            userCurrentPage  =  Integer.parseInt(currentPage)>1?Integer.parseInt(currentPage):1;
        }
        int pageSize = 5;
        if(!StringUtils.isEmpty(size)){
            pageSize=Integer.parseInt(size)>=1?Integer.parseInt(size):5;
        }
        PageBean<Route> pageBean =  routeService.pageQuery(cid,userCurrentPage,pageSize);
        return pageBean;
    }
    @ResponseBody
    @RequestMapping("/findOne")
    public  Route findOne(Integer rid){
        if(rid==null){
            return  new Route();
        }
        Route route = routeService.findOneById(rid);
        if (route==null){
            return  new Route();
        }
        List<RouteImg> list = routeImgService.listRouteImg(rid);
        Seller seller = sellerService.findById(route.getSid());

        route.setRouteImgList(list);//设置风景图信息
        route.setSeller(seller);
//        获取天气信息
        WeatherInfo weatherInfo = routeService.getWeatherInfo(route.getDistrictid());
        route.setWeatherInfo(weatherInfo);

        return route;
    }
}
