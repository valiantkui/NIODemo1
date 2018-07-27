package com.kui.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
	public static void main(String[] args) throws Exception {
		//����ssc����
		ServerSocketChannel ssc = ServerSocketChannel.open();
		//����ͨ��Ϊ������ģʽ
		ssc.configureBlocking(false);
		//��9999�˿�
		ssc.socket().bind(new InetSocketAddress(9999));
		//�ȴ��ͻ�������
		SocketChannel sc = null;
		while(sc==null){
			sc = ssc.accept();
		}
		//����scΪ������ģʽ
		sc.configureBlocking(false);
		
		//�ӿͻ��˶�ȡ����
		
		//--��ȡͷ��Ϣ����ȡ����Ϣ�ĳ���
		String head = "";
		ByteBuffer temp = ByteBuffer.allocate(1);
		while(!head.endsWith("\r\n")){
			sc.read(temp);
			head += new String(temp.array());
			temp.clear();
		}
		int len = Integer.parseInt(head.substring(0,head.length()-2));

		//--����ָ�����ȵĻ���������ȡ����Ϣ
		ByteBuffer buf = ByteBuffer.allocate(len);
		while(buf.hasRemaining()){
			sc.read(buf);
		}
		
		//��ӡ��Ϣ
		String str = new String(buf.array());
		System.out.println(str);
		
		while(true){}
	}
}
