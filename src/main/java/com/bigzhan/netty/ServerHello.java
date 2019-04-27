package com.bigzhan.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther: Gz.
 * @Date: 2019/4/27 17:47
 * @Description: 实现客户端发送一个请求 服务的返回信息
 */
public class ServerHello {

  public static void main(String[] args) throws InterruptedException {
     //定义一对线程组 主从
    //主线程负责接收客户端的链接,但是不做任何处理
    EventLoopGroup masterLoopGroup = new NioEventLoopGroup();
    //从线程组处理逻辑
    EventLoopGroup salverLoogGroup = new NioEventLoopGroup();
    try {
      //netty服务器创建 serverBootStarp 是一个启动类
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(masterLoopGroup,salverLoogGroup) //设置主从线程组
          .channel(NioServerSocketChannel.class)             //设置nio的双向通道
          .childHandler(new ServerHelloInitializer());                               //子处理器

      //启动server 并且设置8088为启动端口号 启动方式为同步
      ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

      //用于监听关闭的channel 方式为同步
      channelFuture.channel().closeFuture().sync();
    }finally {
      masterLoopGroup.shutdownGracefully();
      salverLoogGroup.shutdownGracefully();
    }


  }


}
