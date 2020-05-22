package red.glh.watchdog.service;

/**
 * TestService 服务层实现
 * @author glh
 */
public interface TestService {
    /**
     * 服务层超时
     * @return 3
     */
    int test3();

    /**
     * 自定义服务睡眠时间
     * @param time 多长时间,毫秒
     * @return 4
     */
    int test4(long time);
}
