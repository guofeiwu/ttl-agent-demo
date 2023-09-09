package com.mason.demo.server;

import com.mason.demo.log.TraceChain;
import com.mason.demo.log.TraceHolder;
import com.mason.demo.task.BusTask;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guofei.wu
 * @version v1.0
 * @date 2023/9/9 13:45
 * @since v1.0
 */
public class NettyServiceHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    Logger LOGGER = LoggerFactory.getLogger(NettyServiceHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }


    /**
     * 使用ttl手动操作的方式，子线程中使用未包装的线程池会失效
     *
     * @param channelHandlerContext
     * @param request
     * @throws Exception
     */
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {
//
//        TraceChain traceChain = TraceHolder.get();
//        TraceHolder.set(traceChain);
//        LOGGER.info("nio 线程中的 thread name = {}, traceId = {}", Thread.currentThread().getName(), traceChain.getTraceId());
//        // 通过ttl手动包装子任务，在线程池中可以获取的nio线程中设置的traceid
//        ThreadPoolUtil.execute(TtlRunnable.get(new BusTask(traceChain, "包装子任务")));
//        //=========================
//        // 通过ttl手动包装thread pool，在线程池中可以获取的nio线程中设置的traceid
//        TtlExecutors.getTtlExecutor(ThreadPoolUtil.getThreadPoolExecutor()).execute(new BusTask(traceChain, "包装线程池，不包装子任务"));
//        ByteBuf byteBuf = Unpooled.wrappedBuffer(traceChain.getTraceId().getBytes());
//        FullHttpResponse fullHttpResponse = response(new HttpResponseStatus(200, "ok"), byteBuf);
//        channelHandlerContext.writeAndFlush(fullHttpResponse);
//        TraceHolder.remove();
//    }


    /**
     * 采用java agent的方式
     * <p>
     * 增强 ThreadPoolUtil 和ThreadPoolUtil2
     * 看效果是否和ttl手动包装的一致
     *
     * @param channelHandlerContext
     * @param request
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {

        TraceChain traceChain = TraceHolder.get();
        TraceHolder.set(traceChain);
        LOGGER.info("nio 线程中的 thread name = {}, traceId = {}", Thread.currentThread().getName(), traceChain.getTraceId());
        // 通过ttl手动包装子任务，在线程池中可以获取的nio线程中设置的traceid
        ThreadPoolUtil.execute(new BusTask(traceChain, ""));

        ByteBuf byteBuf = Unpooled.wrappedBuffer(traceChain.getTraceId().getBytes());
        FullHttpResponse fullHttpResponse = response(new HttpResponseStatus(200, "ok"), byteBuf);
        channelHandlerContext.writeAndFlush(fullHttpResponse);
        TraceHolder.remove();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    private FullHttpResponse response(HttpResponseStatus status, ByteBuf content) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        response.headers().set("Content-Type", "text/plain;chaset=utf-8");
        response.headers().set("Content-Length", response.content().readableBytes());
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
