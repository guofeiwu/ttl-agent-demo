package com.mason.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 14:13
 * @since v1.0
 */

@Component
public class NettyServer {
    Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    @Resource
    private SocketInitializer socketInitializer;

    private ServerBootstrap serverBootstrap;

    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public ServerBootstrap getServerBootstrap() {
        return serverBootstrap;
    }

    public void start() throws InterruptedException {
        try {
            this.init();
            ChannelFuture cf = this.serverBootstrap.bind(8888).sync();
            if (cf.isSuccess()) {
                LOGGER.info("netty server started on port 8888 , thread count 1");
                cf.channel().closeFuture().sync();
            } else {
                LOGGER.info("netty server started failed");
            }
        } catch (InterruptedException e) {
            LOGGER.info("netty server started InterruptedException");
        }finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    private void init() throws InterruptedException {
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 123)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(this.socketInitializer);
    }

}
