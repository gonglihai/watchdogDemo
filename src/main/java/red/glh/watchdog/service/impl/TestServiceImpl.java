package red.glh.watchdog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import red.glh.watchdog.service.TestService;
import red.glh.watchdog.watchdog.annotation.WatchDog;

/**
 * TestService 服务层接口
 * @author glh
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    /**
     * service层超时<br/>
     * 执行总计10秒,设置超时时间设置3秒
     * @return 3
     */
    @WatchDog(3000)
    @Override
    public int test3() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 3;
    }

    /**
     * 自定义服务执行时间<br/>
     * 超时时间5秒
     * @param time 多长时间,毫秒
     * @return 4
     */
    @WatchDog
    @Override
    public int test4(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 4;
    }
}
