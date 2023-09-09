package com.mason.demo.log;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 13:55
 * @since v1.0
 */
public class TraceHolder {
    static Logger LOGGER = LoggerFactory.getLogger(TraceHolder.class);

    static ThreadLocal<TraceChain> traceChains = new TransmittableThreadLocal<>();

    public static TraceChain get() {

        TraceChain traceChain = traceChains.get();
        if (traceChain == null) {
            traceChain = new TraceChain();
        }
        return traceChain;
    }

    public static void set(TraceChain traceChain) {
        traceChains.set(traceChain);
    }


    public static void remove() {
        traceChains.remove();
    }
}
