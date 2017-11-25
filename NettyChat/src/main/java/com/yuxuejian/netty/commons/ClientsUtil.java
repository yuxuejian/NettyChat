package com.yuxuejian.netty.commons;

import io.netty.channel.Channel;

import java.util.HashSet;

public class ClientsUtil {
	public static HashSet<Channel> clients = new HashSet<>();
	
	public static void addClient(Channel channel) {
		clients.add(channel);
	}
	
	public static void removeClient(Channel channel) {
		clients.remove(channel);
	}
}
