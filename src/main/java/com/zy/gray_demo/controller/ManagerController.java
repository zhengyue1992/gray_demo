package com.zy.gray_demo.controller;

import com.alibaba.fastjson.JSON;
import com.zy.gray_demo.manager.ClassVersionManager;
import com.zy.gray_demo.manager.GrayManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ManagerController {

    @Autowired
    ClassVersionManager versionManager;
    @Autowired
    GrayManager grayManager;

    @RequestMapping("/version")
    public String versionManager() {
        //通过我们的工厂获取具体实现类
        Map<String, List<Integer>> versionMap = versionManager.getVersionMap();
        return JSON.toJSONString(versionMap);
    }

    @RequestMapping("/gray")
    public String grayManager() {
        //通过我们的工厂获取具体实现类
        Map<String, Integer> grayMap = grayManager.getGrayMap();
        return JSON.toJSONString(grayMap);
    }

    @RequestMapping("/setGray")
    public String setGrayManager(String name, int gray) {
        //通过我们的工厂获取具体实现类
        grayManager.addGrayMap(name, gray);
        return "success";
    }

}
