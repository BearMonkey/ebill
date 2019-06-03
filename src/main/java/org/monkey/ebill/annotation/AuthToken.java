package org.monkey.ebill.annotation;

import java.lang.annotation.*;


/**
 * 自定义注解 @AuthToken，使用该注解的方法或类需要进行Token验证
 * @author monkey
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthToken {
}
