package com.yuxuejian.netty.server;
import com.yuxuejian.netty.handler.ChatInitial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
	
	//����ʹ��netty�������߳�ģ�ͣ�������������Ҫ�������߳�ģ�ͽ�������
	//���д���childǰ׺���Ƕ�workGroup���е�����
	public static void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workGroup)//���������߳�ģ��
		.channel(NioServerSocketChannel.class)//����nio��ͨ��ģʽ
		.childHandler(new ChatInitial())
		.option(ChannelOption.SO_BACKLOG, 128)//�������̳߳ؽ���TCP���ӵĻ����С
		.childOption(ChannelOption.SO_KEEPALIVE, true);//���̳߳�ʱ�̱�������
		
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
