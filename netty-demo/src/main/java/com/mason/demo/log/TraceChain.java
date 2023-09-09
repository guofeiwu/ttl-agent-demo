package com.mason.demo.log;

import java.util.UUID;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 13:56
 * @since v1.0
 */
public class TraceChain {
    private String traceId;
    private String spanId;

    public String getTraceId() {
        return traceId;
    }

    public TraceChain() {
        this.traceId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        this.spanId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }

    @Override
    public String toString() {
        return "TraceChain{" +
                "traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                '}';
    }
}
