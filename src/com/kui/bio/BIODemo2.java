package com.kui.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * �ͻ���
 * @author KUIKUI
 *
 */
public class BIODemo2 {
	public static void main(String[] args) throws IOException {
		demo1();
		//demo2();
		
	}
	
	
	/**
	 * ��ʾ�������д�����ݣ��������������ܣ��Ӷ�out.write����������������
	 * @throws IOException
	 */
	public static void demo1() throws IOException {
		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 9999));
		
		
		OutputStream out = s.getOutputStream();
		
		/**
		 * �ͻ����������д���ݣ�
		 * 1.��������������ܣ������out.write()�����ϲ�������������Tcp�������л�������
		 * ���ͻ��˻Ὣ������д�뻺�����У������������˵�ʱ��Ż����������
		 */
		for(int i=1;i<=Integer.MAX_VALUE;i++){
			out.write("a".getBytes());
			System.out.println("-----"+i);
		}
		
	}
	
	/**
	 * ��ʾ���������д���ݣ������������գ���������Ĵ���in.read������������
	 * @throws IOException 
	 */
	public static void demo2() throws IOException {
		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 9999));
		while(true) {}
	}
}
