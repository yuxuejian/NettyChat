package com.yuxuejian.netty.server;
import com.yuxuejian.netty.handler.ChatInitial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
	
	//本例使用netty的主从线程模型，在启动类中需要对主从线程模型进行配置
	//其中带有child前缀的是对workGroup进行的配置
	public static void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workGroup)//采用主从线程模型
		.channel(NioServerSocketChannel.class)//采用nio的通信模式
		.childHandler(new ChatInitial())
		.option(ChannelOption.SO_BACKLOG, 128)//设置主线程池接受TCP连接的缓存大小
		.childOption(ChannelOption.SO_KEEPALIVE, true);//从线程池时刻保持连接
		
		try {
			Channel channel = b.bind(8081).sync().channel();
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		start();
	}

}
