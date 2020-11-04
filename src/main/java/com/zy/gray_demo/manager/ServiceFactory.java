package com.zy.gray_demo.manager;

import com.zy.gray_demo.annotation.VersionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 被VersionManager注解过的类都可以用这个工厂进行获取
 */
@Component
public class ServiceFactory {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    GrayManager grayManager;

    /**
     * 时间有限，这里只假设拿userId做灰度，具体可以根据自己的业务更换合适字段
     * 或者更改为动态规则
     *
     * @param cla
     * @param userId
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> cla, Long userId) {
        //从容器中获取所有实现类
        Map<String, Object> beans = applicationContext.getBeansOfType((Class<Object>) cla);
        //获取不到，则直接返回null，这里也可以抛出自定义异常
        if (beans == null || beans.isEmpty()) {
            return null;
        }
        //如果只能取到一个ben，则说明不存在多版本切换的情况，直接将这个bean返回即可
        if (beans.size() == 1) {
            return (T) getOne(beans);
        }
        //如果存在多个则说明存在多版本，需要根据灰度获取具体执行策略
        Map<Integer, T> beanMap = new HashMap<>();
        List<Integer> versions = new ArrayList<>(beans.size());
        //循环获取类
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Class thisClass = entry.getValue().getClass();
            VersionManager versionManager = (VersionManager) thisClass.getAnnotation(VersionManager.class);
            int version = versionManager.version();
            beanMap.put(version, (T) applicationContext.getBean(entry.getKey()));
            versions.add(version);
        }
        int version = getVersion(versions, cla.getSimpleName(), userId);
        return beanMap.get(version);
    }

    /**
     * 获取版本号
     * @param versions
     * @param className
     * @param userId
     * @return
     */
    public int getVersion(List<Integer> versions, String className, Long userId) {
        if (versions == null || versions.isEmpty()) {
            return 0;
        }
        //版本排序，这样能够知道最新版本
        Collections.sort(versions);
        //获取是否在灰度内
        boolean gray = gray(className, userId);
        if(gray) {
            //如果在灰度内，则使用最新版本，根据版本定义，越大越新，使用最大版本
            return versions.get(versions.size() - 1);
        } else {
            //如果不在灰度内，则默认获取上一个版本，这样比较保险，或者这里可以自定义获取某个版本
            return versions.get(versions.size() - 2);
        }
    }

    /**
     * 这里只做简单灰度策略
     * 还可以更改为复杂的，自己定义那个区间的执行那个版本，这里不做那么复杂
     * @param userId
     * @return
     */
    public boolean gray(String className, Long userId) {
        if (userId == null) {
            return false;
        }
        //对userId进行取模，根据尾号进行灰度
        long remainder = userId % 100;
        //把灰度按照接口类名存储，根据接口类名进行获取，这样不同的改进可以做不同的灰度
        Integer grayValue = grayManager.getGray(className);
        //如果没有查找到灰度规则，则默认返回不在灰度内
        if (grayValue == null) {
            return false;
        }
        if (remainder < grayValue) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取第一个对象
     * @param beans
     * @return
     */
    private Object getOne(Map<String, Object> beans) {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            return applicationContext.getBean(entry.getKey());
        }
        return null;
    }

}
