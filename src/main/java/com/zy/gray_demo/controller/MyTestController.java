package com.zy.gray_demo.controller;

import com.zy.gray_demo.service.MyTestService;
import com.zy.gray_demo.manager.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyTestController {

    @Autowired
    ServiceFactory serviceFactory;

    @RequestMapping("/test")
    public String test(Long userId) {
        //通过我们的工厂获取具体实现类
        MyTestService service = serviceFactory.getService(MyTestService.class, userId);
        return service.test();
    }



}
