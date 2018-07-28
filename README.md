## 一、NIO -- new IO  -- NonBlocking IO

#### 1.概述
	JDK1.4 中提出的一套新的IO机制，区别与传统的BIO（Blocking IO）

	BIO在高并发场景下遇到了一些无法解决的问题 ---- ACCEPT CONNECT READ WRITE
	针对于每一个客户端都需要在服务器端创建对应线程来处理，线程开辟运行是非常耗费资源的，并且服务器所能支持的最大并发线程数量是有限的，
	所以当高并发到来时，服务器会存在性能瓶颈。
	所以我们想到用少量的线程同时处理多个客户端的连接。而由于传统的BIO阻塞式操作的特点，这种工作方式是无法实现的。

##### BIO:面向流操作字节字符,具有方向性
		InputStream OutputStream Reader Writer
##### NIO:面向通道操作缓冲区，可以双向传输数据
		Channel	Buffer Selector


#### 2.Buffer
	缓冲区，所谓的缓冲区其实就是在内存中开辟的一段连续空间，用来临时存放数据。
	
![avatar](buffer1.png)

	int capacity();
	int position();
	position(int newPosition);
	int limit()
	limit(int newLimit)
		
	//1.创建Buffer
	static ByteBuffer allocate(int capacity)  
	static ByteBuffer wrap(byte[] array) 
	static ByteBuffer wrap(byte[] array, int offset, int length)  

	//2.写数据
	put(byte b) 
	put(byte[] src) 
	putXxx()
	.....

	//3.反转缓冲区
	buf.flip();//此方法等同于buf.limit(buf.position());buf.position(0);

	//4.读取数据
	byte get() 
	get(byte[] dst) 
	......

	//5.判断边界
	int remaning(); //相当于limit - position
	boolean hasRemaning();

	//6.重绕缓冲区
	rewind();

	//7.清空缓冲区
	clear();//将limit设置为capacity大小，postion设置为0，并不真的删除缓冲区中的旧有数据，基于buffer的原理，并不会有问题。

	//8.设置标记，重置标记
	mark();//设置标记
	reset();//重置到标记

