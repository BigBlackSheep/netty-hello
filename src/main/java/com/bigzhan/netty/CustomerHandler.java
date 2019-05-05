package com.bigzhan.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @Auther: Gz.
 * @Date: 2019/4/27 18:12
 * @Description:自定义助手类
 * SimpleChannelInboundHandler 对于请求来讲相当于 [入站,入境]
 */
public class CustomerHandler extends SimpleChannelInboundHandler<HttpObject>{

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject)
      throws Exception {
    if(httpObject instanceof HttpRequest){
      //获取channel
      Channel channel = ctx.channel();
      //打印客户端地址
      System.out.println(channel.remoteAddress());
      //定义发送的数据消息
      ByteBuf context = Unpooled.copiedBuffer("Hello Netty~", CharsetUtil.UTF_8);
      //构建一个http response返回对象
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,context);
      //添加响应数据的类型和长度
      response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
      response.headers().set(HttpHeaderNames.CONTENT_LENGTH,context.readableBytes());
      //把响应刷到客户端
      ctx.writeAndFlush(response);
    }

  }
}
