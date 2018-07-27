package com.kui.nio;

import java.nio.ByteBuffer;

public class BufferDemo1 {
	public static void main(String[] args) {
		//1.创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
//		ByteBuffer buffer2 = ByteBuffer.wrap("abcdefg".getBytes());
	
		//2.写入数据
		buffer.put("abc".getBytes());
		
		//3.反转缓冲区： limint = position position=0
//		buffer.limit(buffer.position());
//		buffer.position(0);
		buffer.flip();
		
		//4.读取缓冲区
//		while(buffer.hasRemaining()){
//			byte b = buffer.get();
//			System.out.println(b);
//		}
		
		//5.重绕缓冲区：position = 0
		buffer.rewind();

//		byte b = buffer.get();
//		System.out.println(b);
		
		//6.清空缓冲区
		buffer.clear();
		
		buffer.put("xy".getBytes());
		
//		buffer.flip();
//		
//		while(buffer.hasRemaining()){
//			byte bb = buffer.get();
//			System.out.println(bb);
//		}
		
		byte c = buffer.get(2);
		System.out.println(c);
	}
}
