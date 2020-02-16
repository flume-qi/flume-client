package com.qi.custom_intercepter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

public class WordCountIntercepter implements Interceptor {

	private String[] splitInfos;
	private String testParam;

	@Override
	public void initialize() {
		// 初始化执行的方法,一般是在自定义的builder内部类里来调用,比方说参数的设置

	}

	@Override
	public Event intercept(Event event) {
		// 处理数据的逻辑代码(单个event的处理)
		String bodyStr = new String(event.getBody());
		splitInfos = bodyStr.split("[\\s,\\.]+");
		event.setBody(String.valueOf(splitInfos.length).getBytes());
		
		if (testParam!=null && !"".equals(testParam)) {
			Map<String, String> headers=new HashMap<>();
			headers.put(testParam, UUID.randomUUID().toString());
			event.setHeaders(headers);
		}
		return event;
	}

	@Override
	public List<Event> intercept(List<Event> events) {
		// 处理数据的逻辑代码(批量event的处理)

		List<Event> results = new ArrayList<>();
		for (Event event : results) {
			results.add(intercept(event));
		}

		return results ;
	}

	@Override
	public void close() {
		// 拦截器被销毁前的资源释放代码
	}

	public static class Builder implements Interceptor.Builder {

		private String param;
		
		@Override
		public void configure(Context context) {
			//接收flume在执行时agent的配置文件中的参数,并且配置到Interceptor上
			param=context.getString("testParam");
		}

		@Override
		public Interceptor build() {
			WordCountIntercepter intercepter=new WordCountIntercepter();
			intercepter.testParam=param;
			return intercepter;
		}

	}
}
