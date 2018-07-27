package com.kui.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServerDemo1 {
	public static void main(String[] args) throws Exception {
		//--定义一个选择器，一般来说全局只有一个
		Selector selc = Selector.open();
		
		//创建ssc对象，开启非阻塞模式
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		//指定监听的端口
		ssc.socket().bind(new InetSocketAddress(9999));

		//--为ssc在selc上注册ACCEPT操作
		ssc.register(selc, SelectionKey.OP_ACCEPT);

		//--开始循环进行select操作，处理就绪的sk
		while(true){
			//--执行selc操作
			//--相当于大喊一声，注册在我身上的sk们，哪一个sk对应的通道已经就绪了当初注册的事件
			int selcCount = selc.select();
			
			//--如果选择出的sk多余0个，表明有需要被处理的通道
			if(selcCount > 0){
				//--选择出已经就绪的sk们
				Set<SelectionKey> set = selc.selectedKeys();
				//--遍历处理sk对应的通道的事件
				Iterator<SelectionKey> it = set.iterator();
				while(it.hasNext()){
						//--遍历出每一个就绪的sk
						SelectionKey sk = it.next();
						//--根据sk注册的不同，分别处理
						if(sk.isAcceptable()){//如果是一个ACCEPT操作
							//--获取sk对应的channel
							ServerSocketChannel sscx = (ServerSocketChannel) sk.channel();
							//--接受连接，得到sc
							SocketChannel sc = sscx.accept();
							//--开启sc的非阻塞模式
							sc.configureBlocking(false);
							//--将sc注册到selc上，关注READ方法
							sc.register(selc, SelectionKey.OP_READ);
						}else if(sk.isConnectable()){//如果是一个Connect操作
							
						}else if(sk.isReadable()){//如果是一个Read操作
							//--获取sk对应的通道
							SocketChannel sc = (SocketChannel) sk.channel();

							//--获取头信息，获知体的长度
							ByteBuffer temp = ByteBuffer.allocate(1);
							String head = "";
							while(!head.endsWith("\r\n")){
								sc.read(temp);
								head += new String(temp.array());
								temp.clear();
							}
							int len = Integer.parseInt(head.substring(0,head.length()-2));
							
							//准备缓冲区接受数据
							ByteBuffer buf = ByteBuffer.allocate(len);
							while(buf.hasRemaining()){
								sc.read(buf);
							}
							
							//打印数据
							String msg = new String(buf.array(),"utf-8");
							System.out.println("服务器收到了客户端["+sc.socket().getInetAddress().getHostAddress()+"]发来的数据："+msg);
							
						}else if(sk.isWritable()){//如果是一个Write操作
							
						}else{//其他就报错
							throw new RuntimeException("NIO操作方式出错！");
						}
					//--从已选择键集中删除已处理过后的键
					it.remove();
				}
				
			}
		}
	}
}
