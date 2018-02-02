package top.lookingsky.nio.service;

import com.alibaba.fastjson.JSON;
import top.lookingsky.nio.util.ServerData;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by 李明 on 2018/2/1.
 * 请求与响应数据处理
 */
public class ServerHandlerRequestAndResponse {
    /**
    * @author LiMing
    * @date 2018/2/1 21:41
    * @Description ## 请求数据处理业务 ##
    * @param data : 接受的客户端请求数据
    * @return object
    */
    public static Object handlerRequest(String data){
        try {
            //解析数据
            ServerData serverData = JSON.parseObject(data, ServerData.class);
            System.out.println(serverData);
            Class className = serverData.getClassName();
            String methodName = serverData.getMethodName();
            Class[] parameterTypes = serverData.getParameterTypes();
            Object[] argsVlue = serverData.getArgsVlue();

            //通过反射调用方法
            Object o = className.newInstance();
            Method method = className.getMethod(methodName, parameterTypes);
            Object invoke = method.invoke(o, argsVlue);
            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    /**
    * @author LiMing
    * @date 2018/2/1 21:44
    * @Description ## 响应数据处理业务 ##
    * @param socketChannel：响应管道
    * @param data：响应数据
    * @return void
    */
    public static void handerRspose(SocketChannel socketChannel,Object data){
        try {
            String str="数据处理"+data;
            ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(byteBuffer);
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
