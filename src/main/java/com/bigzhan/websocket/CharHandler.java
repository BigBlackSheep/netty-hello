package com.bigzhan.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 自定义助手类
 * 处理消息的handler
 * TextWebSocketFrame: 在netty中用于为websocket专门处理文本的对象 frame是消息的载体(单位)
 */
public class CharHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 当客户端连接到服务端之后
     * 获取客户端的channe 并放入到ChannelGroup中 进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //添加到全局
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved. ChannelGroup会自动移除当前channel
        //clients.remove(ctx.channel());
        System.out.println("客户端断开 channel对应的长ID={}" + ctx.channel().id().asLongText());
        System.out.println("客户端断开 channel对应的短ID={}" + ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端发送过来的字符串
        String content = msg.text();
        System.out.println("接收到的消息={}"+ content);

//        clients.forEach(channel->{
//            channel.writeAndFlush(new TextWebSocketFrame("[服务器在:]" + LocalDateTime.now() + "接收到消息,消息为:" + content));
//        });
        //下面这个方法和上面for循环内容一致
        clients.writeAndFlush(new TextWebSocketFrame("[服务器在:]" + LocalDateTime.now() + "接收到消息,消息为:" + content));


    }
}
