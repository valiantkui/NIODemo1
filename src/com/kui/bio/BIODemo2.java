package com.kui.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * 客户端
 * @author KUIKUI
 *
 */
public class BIODemo2 {
	public static void main(String[] args) throws IOException {
		demo1();
		//demo2();
		
	}
	
	
	/**
	 * 演示向服务器写入数据，但服务器不接受，从而out.write（）方法产生阻塞
	 * @throws IOException
	 */
	public static void demo1() throws IOException {
		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 9999));
		
		
		OutputStream out = s.getOutputStream();
		
		/**
		 * 客户端向服务器写数据；
		 * 1.如果服务器不接受，则会在out.write()方法上产生阻塞；但是Tcp连接中有缓冲区，
		 * （客户端会将数据先写入缓冲区中，当缓冲区满了的时候才会产生阻塞）
		 */
		for(int i=1;i<=Integer.MAX_VALUE;i++){
			out.write("a".getBytes());
			System.out.println("-----"+i);
		}
		
	}
	
	/**
	 * 演示不向服务器写数据，但服务器接收，则服务器的代码in.read（）产生阻塞
	 * @throws IOException 
	 */
	public static void demo2() throws IOException {
		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 9999));
		while(true) {}
	}
}
