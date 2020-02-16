package com.qi.util;

import java.nio.charset.Charset;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

public class FlumeClientUtil {

	public static RpcClient client;
	public static final String HOSTNAME="master";
	public static final int PORT=8889;
	
	static{
		client =RpcClientFactory.getDefaultInstance(HOSTNAME, PORT);
	}
	
	public static void sendEvent(String msg) {
		Event event=EventBuilder.withBody(msg,Charset.forName("utf-8"));
		try {
			client.append(event);
			System.out.println("发送消息"+msg+"成功");
		} catch (EventDeliveryException e) {
			System.out.println("发送消息"+msg+"失败");

			e.printStackTrace();
		}
	}
	
	public static void close() {
		if (client!=null) {
			client.close();
		}
	}
}
