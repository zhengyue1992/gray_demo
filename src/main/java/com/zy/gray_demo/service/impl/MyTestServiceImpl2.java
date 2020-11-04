package com.zy.gray_demo.service.impl;

import com.zy.gray_demo.annotation.VersionManager;
import com.zy.gray_demo.service.MyTestService;

@VersionManager(version = 2, createTime = "2020-11-05 12:00:00")
public class MyTestServiceImpl2 implements MyTestService {
    @Override
    public String test() {
        return "这是新方法";
    }
}
