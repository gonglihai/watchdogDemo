package red.glh.watchdog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.glh.watchdog.service.TestService;
import red.glh.watchdog.watchdog.annotation.WatchDog;

/**
 * TestController
 * @author glh
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试服务层
     */
    private final TestService testService;

    /**
     * 注入构造
     * @param testService 测试服务层
     */
    public TestController(TestService testService) {
        this.testService = testService;
    }

    /**
     * 未超时
     * @return 1
     */
    @GetMapping("1")
    public int test1() {
        return 1;
    }

    /**
     * Controller层超时<br/>
     * 执行总计10秒,超时时间设置5秒
     * @return 2
     */
    @GetMapping("2")
    @WatchDog
    public int test2() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }

    /**
     * service层超时<br/>
     * 服务层执行总计10秒,服务层设置超时时间设置3秒
     * @return 3
     */
    @GetMapping("3")
    public int test3() {
        return testService.test3();
    }

    /**
     * 自定义服务执行时间<br/>
     * 超时时间5秒
     * @param time 多长时间,毫秒
     * @return 4
     */
    @GetMapping("4")
    public int test4(long time) {
        return testService.test4(time);
    }

}
