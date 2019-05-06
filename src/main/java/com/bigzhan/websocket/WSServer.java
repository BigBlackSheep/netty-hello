package com.bigzhan.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup mainLoopGroup = new NioEventLoopGroup();
        EventLoopGroup sumLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainLoopGroup,sumLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInItialzer());
            ChannelFuture future = serverBootstrap.bind(8088).sync();
            future.channel().closeFuture().sync();
        }finally {
            mainLoopGroup.shutdownGracefully();
            sumLoopGroup.shutdownGracefully();
        }

    }
}
