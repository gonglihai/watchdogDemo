package red.glh.watchdog.watchdog.entity;

import lombok.Data;
import lombok.ToString;

/**
 * Dog 狗
 * @author glh
 */
@Data
@ToString
public class Dog {
    /**
     * 狗id
     */
    private int id;
    /**
     * 超时时间
     */
    private long timeOut;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 类名
     */
    private String className;
    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 构造
     * @param id         名字
     * @param timeOut    多少毫秒后超时
     * @param methodName 方法名
     * @param className  类名
     */
    public Dog(int id, long timeOut, String methodName, String className) {
        this.id = id;
        this.timeOut = timeOut;
        this.methodName = methodName;
        this.className = className;
        this.createTime = System.currentTimeMillis();
    }

}
