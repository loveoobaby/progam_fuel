package com.yss.secondexample;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx 上下文请求对象
     * @param msg 表示服务端发来的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client 输出:"+msg);
        ctx.writeAndFlush("来自 client："+ LocalDateTime.now());
    }


    /**
     * 如果没有这个方法，Client并不会主动发消息给Server
     * 那么Server的channelRead0无法触发，导致Client的channelRead0也无法触发
     * 这个channelActive可以让Client连接后，发送一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自客户端的问候");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
