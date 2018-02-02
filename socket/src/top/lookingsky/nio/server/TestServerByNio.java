package top.lookingsky.nio.server;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import top.lookingsky.nio.service.ServerHandlerRequestAndResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 李明 on 2018/2/1.
 */
public class TestServerByNio {
    public static void main(String[] args) {
        try {
            /**
             * 设置通信必要组件
             */
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //将ServerSocketChannel的通信模式设置为非阻塞式
            serverSocketChannel.configureBlocking(false);
            //绑定端口,并启动服务器
            serverSocketChannel.bind(new InetSocketAddress(9090));
            //多路复用选择器
            Selector selector = Selector.open();
            //绑定请求事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                //默认阻塞状态，当事件触发就会被唤醒
                int i = selector.select();
                // 获得正在被触发的事件的唯一表示
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //迭代器遍历
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //获得事件的唯一标识
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    /**
                     * 判断事件类型
                     */
                    //如果是接受请求事件
                    if (key.isAcceptable()) {
                        //获取事件的channel对象
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        //连接请求
                        SocketChannel socketChannel = channel.accept();
                        //设置为非阻塞模式
                        socketChannel.configureBlocking(false);
                        //注册读事件
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    /**
                     * 读事件：获得并处理请求数据，注册为写事件
                     */
                    //如果是一个读事件
                    else if (key.isReadable()) {
                        //获得channel对象
                        SocketChannel channel = (SocketChannel) key.channel();
                        //创建字节缓冲区
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        //从管道读取数据存入缓冲区
                        int read = channel.read(byteBuffer);
                        //创建字节输出流，处理缓冲区得到的数据
                        ByteOutputStream byteOutputStream = new ByteOutputStream();
                        while (read != -1) {
                            //反转buffer,,写 --> 读
                            byteBuffer.flip();
                            //当前位置与限制位之间是否还有元素
                            while (byteBuffer.hasRemaining()) {
                                byte b = byteBuffer.get();
                                //将数据写入字节输出流
                                byteOutputStream.write(b);
                            }
                            //缓冲区数据处理完毕之后，清空缓冲区
                            byteBuffer.clear();
                            //继续读取管道数据 写入缓冲区
                            read = channel.read(byteBuffer);
                        }
                        //通过byteOutputStream得到数据
                        String s = byteOutputStream.toString();
                        /**
                         * 请求数据处理业务
                         */
                        System.out.println(s);
                        Object o = ServerHandlerRequestAndResponse.handlerRequest(s);
                        //注册写事件
                        channel.register(selector, SelectionKey.OP_WRITE,o);
                    }
                    /**
                     * 写事件：返回客户端响应数据
                     * 关闭连接
                     */
                    //如果是一个写事件
                    else if (key.isWritable()) {
                        //创建channel对象
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        /**
                         * 响应数据处理业务
                         */
                        //要返回的字符串信息
                        Object data = key.attachment();
                        ServerHandlerRequestAndResponse.handerRspose(socketChannel, data);
                        /*String str = "ceshi";
                        //得到缓冲区
                        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
                        //写出数据
                        socketChannel.write(byteBuffer);
                        //关闭连接
                        socketChannel.close();*/
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
