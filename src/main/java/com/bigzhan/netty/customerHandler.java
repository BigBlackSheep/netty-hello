package com.bigzhan.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;

/**
 * @Auther: Gz.
 * @Date: 2019/4/27 18:12
 * @Description:自定义助手类
 * SimpleChannelInboundHandler 对于请求来讲相当于 [入站,入境]
 */
public class customerHandler extends SimpleChannelInboundHandler<HttpObject>{

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject)
      throws Exception {
    //获取channel
     Channel channel = ctx.channel();
     //打印客户端地址
    System.out.println(channel.remoteAddress());


  }
}
