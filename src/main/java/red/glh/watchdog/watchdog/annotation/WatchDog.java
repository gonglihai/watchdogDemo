package red.glh.watchdog.watchdog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WatchDog注解
 * @author glh
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WatchDog {

    /**
     * 超时时间<br/>
     * 默认5000毫秒(5秒)后超时
     * @return default 5000
     */
    long value() default 5000;

}
