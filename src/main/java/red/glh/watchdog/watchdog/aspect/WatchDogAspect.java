package red.glh.watchdog.watchdog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import red.glh.watchdog.watchdog.WatchDog;
import red.glh.watchdog.watchdog.entity.Dog;

/**
 * WatchDogAspect 看门狗切面
 * @author glh
 */
@Aspect
@Component
public class WatchDogAspect {

    /**
     * 看门狗服务层
     */
    private final WatchDog watchDog;

    /**
     * 注入构造
     * @param watchDog 看门狗服务层
     */
    public WatchDogAspect(WatchDog watchDog) {
        this.watchDog = watchDog;
    }

    /**
     * 定义切点
     */
    @Pointcut("@annotation(red.glh.watchdog.watchdog.annotation.WatchDog)")
    public void ouAspect() {
    }

    /**
     * 进入方法之前
     * @param joinPoint 切点对象
     */
    @Before("ouAspect()")
    public void beforeMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //注解设置的多少毫秒后超时
        long timeout = methodSignature.getMethod().getAnnotation(red.glh.watchdog.watchdog.annotation.WatchDog.class).value();
        //狗id,需要唯一,而且beforeMethod和afterMethod两方法必须都能获取到,暂时取joinPoint的hashCode(不确定是否会重复,如果重复会有bug)
        //todo 1.可将狗的Map变为list或者set,直接存储JoinPoint对象
        //todo 2.或者添加前判断hashCode是否存在,存在另外处理
        int dogId = joinPoint.hashCode();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //类名
        String className = joinPoint.getSignature().getDeclaringTypeName();
        //添加进狗集合
        watchDog.addDog(new Dog(dogId, timeout, methodName, className));
        //启动一个狗线程
        //todo 使用线程池
        DogThread dogThread = new DogThread(dogId, timeout);
        Thread thread = new Thread(dogThread);
        thread.start();
    }

    /**
     * 方法执行之后,喂狗
     * @param joinPoint 切点对象
     */
    @After("ouAspect()")
    public void afterMethod(JoinPoint joinPoint) {
        watchDog.feed(joinPoint.hashCode());
    }

    /**
     * 狗线程
     */
    private class DogThread implements Runnable {
        /**
         * 休眠时间(多长时间超时)
         */
        private final long sleepTime;
        /**
         * 狗id
         */
        private final int dogId;

        /**
         * 构造
         * @param dogId     狗id
         * @param sleepTime 睡眠时间(多长时间超时,毫秒)
         */
        public DogThread(int dogId, long sleepTime) {
            this.dogId = dogId;
            this.sleepTime = sleepTime;
        }

        /**
         * run方法
         * 睡眠sleepTime时间后通过狗id查询狗集合,如果狗还在说明方法还没执行完(超时),狗叫
         */
        @Override
        public void run() {
            try {
                Thread.sleep(sleepTime);
                if (watchDog.dogs.get(dogId) != null) {
                    watchDog.bark(dogId);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
