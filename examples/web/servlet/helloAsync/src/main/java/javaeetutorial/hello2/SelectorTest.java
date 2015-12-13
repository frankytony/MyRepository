package javaeetutorial.hello2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {
	
	private static byte[] request = null;  
	  
    static {  
        StringBuffer temp = new StringBuffer();  
        temp.append("GET http://localhost:8089/webapp1/test1 HTTP/1.1\r\n");  
        temp.append("Host: localhost:8089\r\n");  
        temp.append("Connection: keep-alive\r\n");  
        temp.append("Cache-Control: max-age=0\r\n");  
        temp  .append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11\r\n");  
        temp  .append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");  
        temp.append("Accept-Encoding: gzip,deflate,sdch\r\n");  
        temp.append("Accept-Language: zh-CN,zh;q=0.8\r\n");  
        //temp.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");  
        temp.append("\r\n");  
        request = temp.toString().getBytes();  
    }  
	public static void main(String[] arg) throws IOException{
		final SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8089));  
		Selector selector = Selector.open();  
		socketChannel.configureBlocking(false);  
		SelectionKey key = socketChannel.register(selector, SelectionKey.OP_WRITE);  
		while(true) {  
		  int readyChannels = selector.select();  
		  if(readyChannels == 0) continue;  
		  Set selectedKeys = selector.selectedKeys();  
		  Iterator keyIterator = selectedKeys.iterator();  
		  while(keyIterator.hasNext()) {  
		    SelectionKey key1 = (SelectionKey) keyIterator.next();  
		    if(key1.isAcceptable()) {  
		        // a connection was accepted by a ServerSocketChannel.  
		    } else if (key1.isConnectable()) {  
		        // a connection was established with a remote server.  
		    } else if (key1.isReadable()) {  
		        // a channel is ready for reading  
		    } else if (key1.isWritable()) {  
		    	SocketChannel socketChannel1 =   (SocketChannel) key1.channel();
		    	socketChannel1.write(ByteBuffer.wrap(request));
		    	socketChannel1.socket().shutdownOutput(); 
		    	
		    	int read = 0;  
		         boolean readed = false;  
		         ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲  
		         
		         while ((read = socketChannel.read(buffer)) != -1) {  
		             if (readed) {  
		            	
		            	   System.out.println("----------------0 break");
		            	   break;
		             } else if (read == 0) {  
		            	 System.out.println("----------------0");
		                 continue;  
		             }  

		             buffer.flip();// flip方法在读缓冲区字节操作之前调用。
		             byte[] bArray =  new byte[buffer.limit()];
		             buffer.get(bArray);
		             
		            
		             FileOutputStream file = new FileOutputStream("buffer.txt");
		             file.write(bArray);
		             file.close();
		             // 使用Charset.decode方法将字节转换为字符串  
		             buffer.clear();// 清空缓冲  
		             readed = true;  
		             System.out.println("----------------");
		         }  
		    }  
		  }  
		}  
		
	}
}
