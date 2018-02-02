package top.lookingsky.work.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 李明 on 2018/1/31.
 */

public class WorkClient {
    public static void main(String[] args) {
        byte[] data = new byte[1024];
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文件名");
        String filename = scanner.next();

        Socket socket = null;
        OutputStream outputStream = null;
        PrintWriter printWriter = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            socket = new Socket("127.0.0.1", 9090);
            //向服务端发送文件名
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.println(filename);
            printWriter.flush();
            //在本地创建文件
            File file = new File("D:\\Project\\ideaProject\\BigData\\socket\\server_" + filename);
            //从服务端读取文件
            inputStream = socket.getInputStream();
            fileOutputStream = new FileOutputStream(file);
            int read = 0;
            while (true) {
                read = inputStream.read(data);
                System.out.println(data);
                inputStream.skip(1024);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(data);
                fileOutputStream.flush();
            }
            if(file.length()==0){
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
