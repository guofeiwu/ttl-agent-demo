package com.mason.agent.demo;

import com.alibaba.ttl.threadpool.agent.TtlAgent;
import com.mason.agent.demo.transform.MyClassTransform;

import java.lang.instrument.Instrumentation;

/**
 * Hello world!
 */
public class App {

    public static void premain(String arg, Instrumentation instrumentation) {
        //instrumentation.addTransformer(new MyClassTransform(), true);
        System.out.println("agent的premain(String arg, Instrumentation instrumentation)方法");
        TtlAgent.premain(arg, instrumentation);
    }
}
