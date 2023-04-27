package cn.itcast.travel.service;

import cn.itcast.travel.domain.Order;

public interface OrderService {
    int insert(Order order);
//更新订单状态
    int updateStatus(Order order);

    Order findById(String orderId);
}
