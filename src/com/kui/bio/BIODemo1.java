package com.kui.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ��������
 * @author KUIKUI
 *
 */
public class BIODemo1 {
	public static void main(String[] args) throws Exception {
		demo1();
		
		//demo2();
	}
	
	
	
	/**
	 * ��ʾ�ͻ����������д�����ݣ��������������ܣ��Ӷ��ͻ��˵�out.write����������������
	 * @throws IOException
	 */
	public static void demo1() throws IOException {
		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress(9999));
		Socket s = ss.accept();
		while(true) {}
	}
	
	
	/**
	 * ��ʾ�ͻ��˲��������д���ݣ������������գ���������Ĵ���in.read������������
	 * @throws IOException 
	 */
	public static void demo2() throws IOException {
		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress(9999));
		Socket s = ss.accept();
		InputStream in = s.getInputStream();
		//����ͻ��˲�д�����ݣ��˴����ܻᷢ������
		in.read();
	}
}
