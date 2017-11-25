package com.yuxuejian.netty.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
	
	private int port;
	private String host;
	
	public NettyClient(int port, String host) {
		this.port = port;
		this.host = host;
	}
	
	public void run() {
		EventLoopGroup group = new NioEventLoopGroup();
		
		Bootstrap b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();

		        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		        pipeline.addLast("decoder", new StringDecoder());
		        pipeline.addLast("encoder", new StringEncoder());
		        pipeline.addLast("handler", new ClientHandler());
			}
			
		});
		
		Channel channel;
		try {
			channel = b.connect(host, port).sync().channel();
			System.out.println("¿Í»§¶ËÆô¶¯");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			while(true) {
				channel.writeAndFlush(in.readLine()+"\r\n");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		new NettyClient(8083, "localhost").run();
	}
}
