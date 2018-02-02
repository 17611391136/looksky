package top.lookingsky.work.server;

import top.lookingsky.work.thread.WorkThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李明 on 2018/1/31.
 */
public class WorkServer {
    public static void main(String[] args) {
        ServerSocket serverSocket=null;
        try{
            serverSocket= new ServerSocket(9090);
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new WorkThread(socket));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
