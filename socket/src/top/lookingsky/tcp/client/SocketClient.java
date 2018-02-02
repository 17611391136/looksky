package top.lookingsky.tcp.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 李明 on 2018/1/31.
 */
public class SocketClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try{
            /*Socket socket = new Socket();
            SocketAddress socketAddress=new InetSocketAddress("127.0.0.1", 9090);
            socket.connect(socketAddress);*/

            Socket socket = new Socket("127.0.0.1", 9090);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            //键盘输入
            System.out.println("请输入要发送的数据，退出输入 exit");
            String date = scanner.next();
            if("exit".equals(date)){
                return;
            }
            printWriter.println(date);
            printWriter.flush();

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine = bufferedReader.readLine();
            System.out.println("客户端接收---->"+readLine);

            //关闭资源
            inputStream.close();
            printWriter.close();
            outputStream.close();
            bufferedReader.close();

            socket.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
