package com.zy.gray_demo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.util.Date;

@Documented//若使用javadoc生成文档时 将注解也生成文档则加入该注解
@Order//spring的注解 会传入一个常数 数字越小优先加载等级会越高
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})//表示该自定义注解只能使用在方法上
@Retention(RetentionPolicy.RUNTIME)//表示该注解的声明周期 将注解保留在运行时
@Component
public @interface VersionManager {

    @AliasFor(
            annotation = Component.class
    )
    String value() default "";

    /**
     * 版本号，数字越大，表示版本越新
     * @return
     */
    int version() default 0;

    /**
     * 创建时间
     * @return
     */
    String createTime() default "";

}
