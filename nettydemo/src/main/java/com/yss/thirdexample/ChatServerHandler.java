package com.yss.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);



    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.writeAndFlush("[服务器]：" + ctx.channel().remoteAddress() + " 加入\n");
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clients.writeAndFlush("[服务器]：" + ctx.channel().remoteAddress() + " 离开\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clients.writeAndFlush("[服务器]：" + ctx.channel().remoteAddress() + " 上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clients.writeAndFlush("[服务器]：" + ctx.channel().remoteAddress() + " 下线\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        clients.forEach(ch->{
            if(channel != ch){
                ch.writeAndFlush(channel.remoteAddress() + " 发送消息：" + msg);
            }else {
                channel.writeAndFlush("[自己]:" + msg);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
