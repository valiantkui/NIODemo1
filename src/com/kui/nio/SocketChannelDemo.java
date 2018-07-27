package com.kui.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
	public static void main(String[] args) throws Exception {
		//创建sc对象
		SocketChannel sc = SocketChannel.open();
		//开始非阻塞模式
		sc.configureBlocking(false);
		//开始连接服务器
		sc.connect(new InetSocketAddress("127.0.0.1",9999));
		//完成对服务器的连接
		while(!sc.finishConnect()){
			Thread.sleep(10);
		}

		//要发送的数据
		String str = "hello nio~ i am piao qian";
		
		//准备数据，格式为 数据长度\r\n数据内容
		StringBuffer sb = new StringBuffer();
		sb.append(str.length()+"\r\n");
		sb.append(str);
		String sendStr = sb.toString();
		
		//发送数据
		ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes());
		while(buf.hasRemaining()){
			sc.write(buf);
		}
		
		
		while(true){}
	
//		for(int i=1;i<=Integer.MAX_VALUE;i++){
//			int c = sc.write(ByteBuffer.wrap("a".getBytes()));
//			System.out.println("第"+i+"次输出，输出了字节数："+c);
//		}
	}
}
