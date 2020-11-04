package com.zy.gray_demo.manager;

import com.zy.gray_demo.annotation.VersionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里只做了简单的获取所有版本控制的类的信息
 * 可以用于做管理后台，查看每个超类对应的版本
 * 还可以与灰度结合，做一个综合的管理后台
 */
@Component
public class ClassVersionManager {

    private Map<String, List<Integer>> versionMap;

    @Autowired
    public ClassVersionManager(ApplicationContext applicationContext) {
        versionMap = new HashMap<>();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(VersionManager.class);
        //循环获取类
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class thisClass = entry.getValue().getClass();
            Class[] interfaces = thisClass.getInterfaces();
            for (Class thisInterfaces :interfaces) {
                String interfaceName = thisInterfaces.getSimpleName();
                List<Integer> versions = versionMap.get(interfaceName);
                if (versions == null) {
                    versions = new ArrayList<>();
                }
                VersionManager versionManager = (VersionManager) thisClass.getAnnotation(VersionManager.class);
                versions.add(versionManager.version());
                versionMap.put(interfaceName, versions);
            }
        }
    }

    /**
     * 获取所有的类的版本信息
     * @return
     */
    public Map<String, List<Integer>> getVersionMap() {
        return versionMap;
    }

}
