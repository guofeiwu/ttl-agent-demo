package com.mason.demo.task;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 16:48
 * @since v1.0
 */
public class PrintUtil {
    public static String print(String parentTrace, String currentThreadGetTrace) {
        if (parentTrace.equals(currentThreadGetTrace)) {
            return "====>traceId一致 === true";
        }
        return "====>traceId不一致 === false !!!";
    }
}
