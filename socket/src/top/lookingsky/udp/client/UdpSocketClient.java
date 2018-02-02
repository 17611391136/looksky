package top.lookingsky.udp.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by 李明 on 2018/2/1.
 */
public class UdpSocketClient {
    public static void main(String[] args) {
        try {
            //创建客户端socket，端口有系统随机确定
            DatagramSocket socket = new DatagramSocket();
            //要发送的数据
            String strData = "测试数据007";
            byte[] data = strData.getBytes();
            //将服务器信息、数据绑定到DatagramPacket,服务器信息也可以通过setSocketAddress设置
            DatagramPacket packet = new DatagramPacket(data, 0, data.length, new InetSocketAddress("127.0.0.1", 9090));
            //向服务器发送数据
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
