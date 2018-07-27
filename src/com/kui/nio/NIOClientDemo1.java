package com.kui.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClientDemo1 {
	public static void main(String[] args) throws Exception {
		//����һ��ѡ������ͨ����ȫ��Ψһ��
		Selector selc = Selector.open();
		
		//����ss����
		SocketChannel sc = SocketChannel.open();
		//����������ģʽ
		sc.configureBlocking(false);
		//��ssע�ᵽselc�Ϲ�עCONNECT�¼�
		sc.register(selc, SelectionKey.OP_CONNECT);
		//��ʼ����
		sc.connect(new InetSocketAddress("127.0.0.1",9999));
		
		//--��ʼѭ������select���������������sk
		while(true){
			//--ִ��selc����
			//--�൱�ڴ�һ����ע���������ϵ�sk�ǣ���һ��sk��Ӧ��ͨ���Ѿ������˵���ע����¼�
			int selcCount = selc.select();
			
			if(selcCount > 0){
				//--��ȡ�Ѿ�������sk��
				Set<SelectionKey> set = selc.selectedKeys();
				//--��������sk
				Iterator<SelectionKey> it = set.iterator();
				while(it.hasNext()){
					//--��ȡ�������ļ�
					SelectionKey sk = it.next();
					//--��ȡ����Ӧ��ͨ��
					SocketChannel scx = (SocketChannel) sk.channel();
					
					if(sk.isConnectable()){//��һ��CONNECT����
						//--�������
						scx.finishConnect();
						//--��scע�ᵽselc�Ϲ�עWRITE����
						scx.register(selc, SelectionKey.OP_WRITE);
					}else if(sk.isWritable()){//��һ��WRITE����
						//--�����͵�����
//						String str = "hello nio~ hello world~";
						String str = "��� nio~ ������������������~~~";
						//--����Э��
						String sendStr = str.getBytes("utf-8").length +"\r\n" + str;
						
						//--��������
						ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes("utf-8"));
						while(buf.hasRemaining()){
							scx.write(buf);
						}
						
						//--ȡ��ע��
						sk.cancel();
					}
				}
				
				//--����ѡ�������ɾ���Ѿ�������ɵļ�
				it.remove();
			}
		}
	}
}
