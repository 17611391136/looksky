package top.lookingsky.udp.server;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by 李明 on 2018/2/1.
 */
public class UdpSocketServer {
    public static void main(String[] args) {
        try {
            //为服务端绑定端口
            DatagramSocket socket=new DatagramSocket(9090);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
