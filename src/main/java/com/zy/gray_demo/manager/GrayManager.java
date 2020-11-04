package com.zy.gray_demo.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 灰度管理类，这里可以项目中可以直接做持久化，这里为了方便，存储在内存中，重启会消失
 */
@Component
public class GrayManager {

    //灰度策略
    private Map<String, Integer> grayMap = new HashMap<>();

    /**
     * 获取灰度
     * @param className
     * @return
     */
    public Integer getGray(String className) {
        return grayMap.get(className);
    }

    /**
     * 获取灰度map
     * @return
     */
    public Map<String, Integer> getGrayMap() {
        return grayMap;
    }

    /**
     * 设置灰度的方法
     * @param className
     * @param gray
     */
    public void addGrayMap(String className, int gray) {
        grayMap.put(className, gray);
    }

}
