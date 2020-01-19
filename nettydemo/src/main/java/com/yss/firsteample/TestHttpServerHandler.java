package com.yss.firsteample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channel active");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("channel register");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channel inactive");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        System.out.println("handler added");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("channel unregister");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());
        if(msg instanceof HttpRequest){
            System.out.println("remote address = " + ctx.channel().remoteAddress());
            Thread.sleep(8000);

            HttpRequest request = (HttpRequest) msg;
//            System.out.println("请求方法名称：" + request.method());
            System.out.println("url = " +  request.uri());
            String  url = request.uri();
            if("/favicon.ico".equals(url)){
                return;
            }
            ByteBuf context = Unpooled.copiedBuffer("hello world\n", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, context);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(HttpHeaderNames.CONTENT_LENGTH, context.readableBytes());
            ctx.writeAndFlush(response);
            ctx.channel().close();
        }
    }
}
