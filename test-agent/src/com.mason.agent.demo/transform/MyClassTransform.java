package com.mason.agent.demo.transform;


import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 18:06
 * @since v1.0
 */
public class MyClassTransform  implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        return new byte[0];
    }
}
