package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.OrderDao;
import cn.itcast.travel.domain.Order;
import cn.itcast.travel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  java.util.Date;

@Service
public  class OrderServiceImpl  implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public int insert(Order order) {
        order.setStatus("0");
        Date date = new Date();
        order.setCreatetime(date);
        order.setUpdatetime(date);

        return orderDao.insert(order);

    }

    @Override
    public  int updateStatus(Order order){
        Date date = new Date();
        order.setUpdatetime(date);
        order.setPaymenttime(date);
        return orderDao.updataStatus(order);
    }
    @Override
    public  Order findById(String orderId){
        return  orderDao.findById(orderId);
    }

}
