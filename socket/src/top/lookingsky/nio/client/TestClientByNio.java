package top.lookingsky.nio.client;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import top.lookingsky.nio.entity.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Created by 李明 on 2018/2/1.
 */
public class TestClientByNio {
    public static void main(String[] args) {
        try {
            //连接服务器
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9090));
            /**
             * 发送数据
             * className   Clsdd 目标类
             * methodName  String方法名
             * parameterTypes  Class[]参数类型
             * argsValue    Object[] 参数
             */
            String className="top.lookingsky.nio.service.UserServiceImpl";
            String methodName="add";
            Class[] parameterTypes=new Class[]{User.class};
            Object[] argsValue=new Object[]{new User("1","ceshi")};
            HashMap<String, Object> map = new HashMap<>();
            map.put("className",className);
            map.put("methodName",methodName);
            map.put("parameterTypes",parameterTypes);
            map.put("argsValue",argsValue);
            String str = JSON.toJSONString(map);
            //String str = "客户端 请求的 数据 svdfcsdas ajshbdfuasfc";
            //获得byteBuffer
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
            //向服务端发送数据
            socketChannel.write(byteBuffer);
            //关闭套接字socket的输出流
            socketChannel.socket().shutdownOutput();

            //获得服务端响应数据
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //从管道读取数据，存入buffer
            int i = socketChannel.read(readBuffer);
            //创建字节输出流，输出响应数据
            ByteOutputStream byteOutputStream = new ByteOutputStream();
            /**
             * 处理请求数据
             */
            while (i != -1) {
                //反转缓冲区  写-->读
                readBuffer.flip();
                //当前位置与限制位之间是否还有元素
                while (readBuffer.hasRemaining()) {
                    byte b = readBuffer.get();
                    byteOutputStream.write(b);
                }
                //清空缓冲区
                readBuffer.clear();
                //重新读取数据
                i=socketChannel.read(readBuffer);
            }
            //响应数据的字符串形式
            String s = byteOutputStream.toString();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
