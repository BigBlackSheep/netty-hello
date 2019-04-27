package com.bigzhan.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Auther: Gz.
 * @Date: 2019/4/27 18:03
 * @Description: 初始化器,channel 注册后会执行里面相应的方法
 */
public class ServerHelloInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    //通过socketChannel获得对应的管道
    ChannelPipeline pipeline = socketChannel.pipeline();
    //通过管道添加ChannelHandler
    //HttpServerCodec是由netty自己提供的助手类 可以理解为拦截器
    //当请求到服务端 我们需要做解码 相应客户端做编码
    pipeline.addLast("HttpServerCodec",new HttpServerCodec());
    //添加自定义的助手类
    pipeline.addLast("customerHandler",null);
  }
}
