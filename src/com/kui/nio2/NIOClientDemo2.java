package com.kui.nio2;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClientDemo2 {
	public static void main(String[] args) throws Exception {
		//����һ��ѡ������ͨ����ȫ��Ψһ��
		Selector selc = Selector.open();
		
		//����sc����
		SocketChannel sc = SocketChannel.open();
		//����������ģʽ
		sc.configureBlocking(false);
		//��ssע�ᵽselc�Ϲ�עCONNECT�¼� READ�¼� WRITE�¼�
		sc.register(selc, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		
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
					}else if(sk.isWritable()){//��һ��WRITE����
						//--�����͵�����
						String str = "��� nio~ ������������������~~~";
						//--����Э��
						String sendStr = str.getBytes("utf-8").length +"\r\n" + str;
						
						//--��������
						ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes("utf-8"));
						while(buf.hasRemaining()){
							scx.write(buf);
						}
						//--ȡ��WRITEע��
						scx.register(selc, sk.interestOps() & ~SelectionKey.OP_WRITE);
					}else if(sk.isReadable()){//�����һ��Read����
						//��ȡͷ��Ϣ
						ByteBuffer temp = ByteBuffer.allocate(1);
						String head = "";
						while(!head.endsWith("\r\n")){
							scx.read(temp);
							head += new String(temp.array());
							temp.clear();
						}
						int len = Integer.parseInt(head.substring(0,head.length()-2));
						
						//׼����������������
						ByteBuffer buf = ByteBuffer.allocate(len);
						while(buf.hasRemaining()){
							scx.read(buf);
						}
						
						//��ӡ����
						String msg = new String(buf.array(),"utf-8");
						System.out.println("�յ��˷��������� ����Ϣ��"+msg);
					}
				}
				
				//--����ѡ�������ɾ���Ѿ�������ɵļ�
				it.remove();
			}
		}
	}
}
