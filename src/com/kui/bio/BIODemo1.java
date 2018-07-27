package com.kui.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端
 * @author KUIKUI
 *
 */
public class BIODemo1 {
	public static void main(String[] args) throws Exception {
		demo1();
		
		//demo2();
	}
	
	
	
	/**
	 * 演示客户端向服务器写入数据，但服务器不接受，从而客户端的out.write（）方法产生阻塞
	 * @throws IOException
	 */
	public static void demo1() throws IOException {
		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress(9999));
		Socket s = ss.accept();
		while(true) {}
	}
	
	
	/**
	 * 演示客户端不向服务器写数据，但服务器接收，则服务器的代码in.read（）产生阻塞
	 * @throws IOException 
	 */
	public static void demo2() throws IOException {
		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress(9999));
		Socket s = ss.accept();
		InputStream in = s.getInputStream();
		//如果客户端不写入数据，此处可能会发生阻塞
		in.read();
	}
}
