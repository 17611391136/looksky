package top.lookingsky.tcp.thread;

import java.io.*;
import java.net.Socket;

/**
 * Created by 李明 on 2018/1/31.
 */
public class ServerThread implements Runnable{

    private Socket socket;

    public ServerThread() {
    }

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        try{
            //获得输入流
            inputStream = socket.getInputStream();
            //获得字符缓冲输入流
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //读取数据
            String readLine = bufferedReader.readLine();
            System.out.println("服务端接收--->" + readLine);
            //将读取到的数据转换成大写
            String upperCase = readLine.toUpperCase();
            //获得输出流
            outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("全部转换成大写"+upperCase);
            printWriter.flush();
            //关闭资源
            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStream.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
