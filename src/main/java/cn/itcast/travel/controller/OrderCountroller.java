package cn.itcast.travel.controller;

import cn.itcast.travel.domain.Order;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.OrderService;
import cn.itcast.travel.service.WXPayService;
import cn.itcast.travel.utils.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderCountroller {
    @Autowired
    private OrderService orderService;

    @Autowired
    private WXPayService wxPayService;


    @ResponseBody
    @RequestMapping("/add")
    public ResultInfo add(Order order) {
        String payment = order.getPayment() + "";
        String money = PayUtil.getMoney(payment);
        String id = PayUtil.getId("01");
        Map<String, String> aNative = wxPayService.createNative(id, money, order.getGoods());
        System.out.println(aNative);
        if (!("SUCCESS".equals(aNative.get("return_code")) && "SUCCESS".equals(aNative.get("return_code")))) {
            return new ResultInfo(false, "订单创建失败");
        }

        order.setOrderid(id);
        orderService.insert(order);
        aNative.put("orderId", id);
        aNative.put("total_fee", money);
        return new ResultInfo(true, aNative, "创建成功");
    }

    @ResponseBody
    @PostMapping ("/findPayStatus")
    public ResultInfo findPayStatus(String orderId){
        Map<String ,String>map = wxPayService.queryNative(orderId);
        if(map==null||!"SUCCESS".equals(map.get("return_code"))){
            return  new ResultInfo(true,"-1","支付发生错误");
        }
        if("SUCCESS".equals(map.get("trade_state"))){
            Order order=new Order();
            order.setOrderid(orderId);
            order.setStatus("1");
            orderService.updateStatus(order);
            return  new ResultInfo(true,"1","支付成功");        }
//        判断订单是否超市 超市关闭订单
        Order order = orderService.findById(orderId);
        Date createtime = order.getCreatetime();
        long time = createtime.getTime();
        long currTime = new Date().getTime();
        if(currTime-time>1000*60*5){
            return  new ResultInfo(false,"2","假装订单已关闭");
        }
        return  new ResultInfo(false,"0","未支付");
    }
}
