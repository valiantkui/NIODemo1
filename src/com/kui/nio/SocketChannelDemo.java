package com.kui.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
	public static void main(String[] args) throws Exception {
		//����sc����
		SocketChannel sc = SocketChannel.open();
		//��ʼ������ģʽ
		sc.configureBlocking(false);
		//��ʼ���ӷ�����
		sc.connect(new InetSocketAddress("127.0.0.1",9999));
		//��ɶԷ�����������
		while(!sc.finishConnect()){
			Thread.sleep(10);
		}

		//Ҫ���͵�����
		String str = "hello nio~ i am piao qian";
		
		//׼�����ݣ���ʽΪ ���ݳ���\r\n��������
		StringBuffer sb = new StringBuffer();
		sb.append(str.length()+"\r\n");
		sb.append(str);
		String sendStr = sb.toString();
		
		//��������
		ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes());
		while(buf.hasRemaining()){
			sc.write(buf);
		}
		
		
		while(true){}
	
//		for(int i=1;i<=Integer.MAX_VALUE;i++){
//			int c = sc.write(ByteBuffer.wrap("a".getBytes()));
//			System.out.println("��"+i+"�������������ֽ�����"+c);
//		}
	}
}
