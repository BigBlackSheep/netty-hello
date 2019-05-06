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
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    System.out.println("注册");
    super.channelRegistered(ctx);
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    System.out.println("移除");
    super.channelUnregistered(ctx);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("活跃");
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("断开连接(不活跃)");
    super.channelInactive(ctx);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    System.out.println("数据读取完毕");
    super.channelReadComplete(ctx);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    System.out.println("用户事件触发");
    super.userEventTriggered(ctx, evt);
  }

  @Override
  public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channel可写事件更改");
    super.channelWritabilityChanged(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println("异常....可关闭资源");
    super.exceptionCaught(ctx, cause);
  }


  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    System.out.println("助手类添加");
    super.handlerAdded(ctx);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    System.out.println("助手类移除");
    super.handlerRemoved(ctx);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject)
      throws Exception {

    //只接收Http请求
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
