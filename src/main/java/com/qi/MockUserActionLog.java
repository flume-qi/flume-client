package com.qi;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

public class MockUserActionLog {

	public static String[] provinces=new String[]{"beijing","shanghai","henan","shenzhen","tianjin","hainan"};
	private Random random=new Random();
	private RpcClient rpcClient=RpcClientFactory.getDefaultInstance("master", 8888);
	
	public Event randomEvent(String msg) {
		
		String province=provinces[random.nextInt(6)];
		Event event=EventBuilder.withBody(msg+"---"+province,Charset.forName("utf-8"));
		System.out.println("创建event:"+msg+"---"+province);
		Map<String, String> header =new HashMap<>();
		header.put("address", province);
		event.setHeaders(header);
		return event;
	}
	
	public void sendMsg(Event event) {
		try {
			rpcClient.append(event);
		} catch (EventDeliveryException e) {
			System.out.println("发送event"+event+"失败");
			e.printStackTrace();
		}
	}
	
	public void close() {
		if (rpcClient!=null) {
			rpcClient.close();
		}
	}
	
	public static void main(String[] args) {
		MockUserActionLog test=new MockUserActionLog();
		for (int i = 0; i < 100; i++) {
			Event event=test.randomEvent("msg"+i);
			test.sendMsg(event);
		}
		test.close();
	}
	
}


















