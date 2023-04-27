package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Order;

public interface OrderDao {
    int insert(Order order);

    Order findById(String orderId);

    int updataStatus(Order order);
}
