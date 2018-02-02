package top.lookingsky.work.thread;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

/**
 * Created by 李明 on 2018/1/31.
 */
public class WorkThread implements Runnable {
    private Socket socket;

    public WorkThread() {
    }

    public WorkThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] data = new byte[1024];
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String filename = bufferedReader.readLine();
            outputStream = socket.getOutputStream();
            //获得文件输入流，读取本地文件
            System.out.println(filename);
            FileInputStream fileInputStream = new FileInputStream(new File("D:\\Project\\ideaProject\\BigData\\socket\\" + filename));
            int read = 0;
            while (true) {
                read = fileInputStream.read(data);
                if (read == -1) {
                    break;
                }
                //从当前输入流中跳过并丢弃 n 个字节的数据
                fileInputStream.skip(1024);
                //输出数据
                outputStream.write(data);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
