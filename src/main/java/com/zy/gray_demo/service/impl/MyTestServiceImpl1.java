package com.zy.gray_demo.service.impl;

import com.zy.gray_demo.annotation.VersionManager;
import com.zy.gray_demo.service.MyTestService;

@Deprecated
@VersionManager(version = 1, createTime = "2020-11-04 12:00:00")
public class MyTestServiceImpl1 implements MyTestService {
    @Override
    public String test() {
        return "这是旧方法";
    }
}
