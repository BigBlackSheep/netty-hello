package com.bigzhan.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInItialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //websocket 基于Http协议 所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对HttpMessage进行聚合 聚合成FullHttpRequest 或 FullHttpResponse
        //几乎所以netty编程 都会是到次handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        //============================以上是用于支持Http协议====================================

        //websocket 服务器处理的协议 用于指定给客户端连接访问的路由: /ws
        //本handler 会帮你处理一些繁重复杂的事
        //会帮你处理握手动作 handshaking  (Close, Ping, Pong) ping + pong = 心跳
        //对于我们的websocket来讲 都是以feams进行传输的 不同的数据类型对应的frames也不同
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义handler
        pipeline.addLast(new CharHandler());
    }
}
