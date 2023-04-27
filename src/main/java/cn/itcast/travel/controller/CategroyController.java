package cn.itcast.travel.controller;


import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/category")
public class CategroyController {
    @Autowired
    private CategroyService categroyService;
    @ResponseBody
    @GetMapping("/findAll")
    public  List<Category> findAll(){
//        调用业务逻辑层
         List<Category> list = categroyService.findAll();
         return list;
    }
}
