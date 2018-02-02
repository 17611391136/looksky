package top.lookingsky.tcp.server;

import top.lookingsky.tcp.thread.ServerThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李明 on 2018/1/31.
 */
public class SocketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            //创建9090端口的serverSocket
            serverSocket = new ServerSocket(9090);

            ExecutorService pool = Executors.newFixedThreadPool(5);
            while (true) {
                //侦听并接受连接
                    socket = serverSocket.accept();
                    pool.execute(new ServerThread(socket));
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
