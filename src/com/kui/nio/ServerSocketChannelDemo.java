package com.kui.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
	public static void main(String[] args) throws Exception {
		//创建ssc对象
		ServerSocketChannel ssc = ServerSocketChannel.open();
		//设置通道为非阻塞模式
		ssc.configureBlocking(false);
		//绑定9999端口
		ssc.socket().bind(new InetSocketAddress(9999));
		//等待客户端连接
		SocketChannel sc = null;
		while(sc==null){
			sc = ssc.accept();
		}
		//设置sc为非阻塞模式
		sc.configureBlocking(false);
		
		//从客户端读取数据
		
		//--读取头信息，获取体信息的长度
		String head = "";
		ByteBuffer temp = ByteBuffer.allocate(1);
		while(!head.endsWith("\r\n")){
			sc.read(temp);
			head += new String(temp.array());
			temp.clear();
		}
		int len = Integer.parseInt(head.substring(0,head.length()-2));

		//--创建指定长度的缓冲区，读取体信息
		ByteBuffer buf = ByteBuffer.allocate(len);
		while(buf.hasRemaining()){
			sc.read(buf);
		}
		
		//打印信息
		String str = new String(buf.array());
		System.out.println(str);
		
		while(true){}
	}
}
