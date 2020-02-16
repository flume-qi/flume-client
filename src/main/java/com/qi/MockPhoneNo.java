package com.qi;

import java.util.Random;

import com.qi.util.FlumeClientUtil;

public class MockPhoneNo {

	public static Random random=new Random();
	public static String[] phonePrefix=new String[]{
			"131","133","135","158","183"
	};
	
	public String[] names=new String[]{"jim","zhangsan","tom","jery","red"};
	
	public static String[] words="tjtyjntyjnmtyjmnghnrtgjrygjnbrtfdhbdfgvbsdvgfszfszfdnnbdbddfrbXC".split("[\\s,\\.]+");
	
	public String mockPhoneNo() {
		
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(phonePrefix[random.nextInt(phonePrefix.length)]);
		for (int i = 0; i < 8; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		
		return stringBuilder.toString();
	}
	
	public String mockUserInfo(int id) {
		
		String name =names[random.nextInt(names.length)];
		String gender=random.nextInt(2)==0?"femal":"male";
		int age=1+random.nextInt(60);
		
		return id+","+name+","+gender+","+age;
	}
	
	public String mockStringLine() {
		
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < (1+random.nextInt(60)); i++) {
			sb.append(words[random.nextInt(words.length)]+" ");
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		MockPhoneNo mockPhoneNo=new MockPhoneNo();
		for (int i = 0; i < 10; i++) {
//			System.out.println(mockPhoneNo.mockPhoneNo());
//			String phoneValue = mockPhoneNo.mockPhoneNo();
//			FlumeClientUtil.sendEvent(phoneValue);
			
//			String mockUserInfo = mockPhoneNo.mockUserInfo(i);
//			System.out.println(mockUserInfo);
			
//			FlumeClientUtil.sendEvent(mockUserInfo);
			
//			String line=mockPhoneNo.mockStringLine();
			String line=i+1+"\t"+"王五"+"\t"+15;
			FlumeClientUtil.sendEvent(line);
		}
	FlumeClientUtil.close();
	}
}
