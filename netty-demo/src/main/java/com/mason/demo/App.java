package com.mason.demo;

import com.mason.demo.server.NettyServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


/**
 * Hello world!
 */
@SpringBootApplication
public class App implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Resource
    private NettyServer nettyServer;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyServer.start();
    }
}
