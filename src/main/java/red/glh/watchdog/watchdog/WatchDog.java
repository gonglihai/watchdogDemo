package red.glh.watchdog.watchdog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import red.glh.watchdog.watchdog.entity.Dog;

import java.util.Hashtable;
import java.util.Map;

/**
 * WatchDog 看门狗服务层
 * @author glh
 */
@Slf4j
@Component
public class WatchDog {
    /**
     * 狗集合
     */
    public final Map<Integer, Dog> dogs = new Hashtable<>();

    /**
     * 添加狗
     * @param dog 狗
     */
    public void addDog(Dog dog) {
        this.dogs.put(dog.getId(), dog);
    }

    /**
     * 喂狗
     * @param id 狗id
     */
    public void feed(int id) {
        this.dogs.remove(id);
    }

    /**
     * 狗叫
     * @param id 狗id
     */
    public void bark(int id) {
        //获取狗信息
        Dog dog = this.dogs.get(id);
        //移出狗Map集合
        this.feed(id);
        log.error("汪汪汪,操作超时,狗信息:" + dog);
        //todo 超时业务
    }
}
