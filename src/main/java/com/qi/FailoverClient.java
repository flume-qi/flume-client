package com.qi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

public class FailoverClient {

	private RpcClient client;
	
	public FailoverClient() {
		//初始化client
		
			try {
				this.client = RpcClientFactory.getInstance(new File("src/main/resources/failover.properties"));
			} catch (FileNotFoundException e) {
				System.out.println("没找到文件");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("文件读取异常");
				e.printStackTrace();
			}
	}
	
	//发送消息
		public void sendMessage(String msg) {
			
			Event event=EventBuilder.withBody(msg,Charset.forName("utf-8"));
			try {
				client.append(event);
			} catch (EventDeliveryException e) {
				System.out.println("发送消息"+msg+"失败");
				e.printStackTrace();
			}
		}
		
		//关闭客户端
		public void close() {
			if (client!=null) {
				client.close();
			}
		}
		
		public static void main(String[] args){
			FailoverClient testClient=new FailoverClient();
			for (int i = 0; i < 100; i++) {
				testClient.sendMessage("msg-"+i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("发送失败");
					e.printStackTrace();
				}
			}
		}
		
}



















