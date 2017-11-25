package com.yuxuejian.netty.handler;

import com.yuxuejian.netty.commons.ClientsUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		for (Channel c : ClientsUtil.clients) {
			c.writeAndFlush("【"+channel.remoteAddress()+"】:上线了"+"\n");
			System.out.println("【"+channel.remoteAddress()+"】:上线了");
		}
		ClientsUtil.addClient(channel);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Channel channel = ctx.channel();
		for (Channel c : ClientsUtil.clients) {
			if (c != channel) {
				c.writeAndFlush("【"+c.remoteAddress()+"】:"+msg+"\n");
				System.out.println("【"+c.remoteAddress()+"】:"+msg+"\n");
			} else {
				channel.writeAndFlush("【you】:"+msg+"\n");
				System.out.println("【you】:"+msg+"\n");
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel channel = ctx.channel();
		for (Channel c : ClientsUtil.clients) {
			c.writeAndFlush("【"+channel.remoteAddress()+"】:下线了");
			System.out.println("【"+channel.remoteAddress()+"】:下线了");
		}
		ClientsUtil.removeClient(channel);
	}

}
