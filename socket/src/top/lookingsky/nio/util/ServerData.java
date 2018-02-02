package top.lookingsky.nio.util;

import java.util.Arrays;

/**
 * Created by 李明 on 2018/2/1.
 */
public class ServerData {
    private Class className;
    private  String methodName;
    private Class[] parameterTypes;
    private Object[] argsVlue;

    public ServerData() {
    }

    public ServerData(Class className, String methodName, Class[] parameterTypes, Object[] argsVlue) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.argsVlue = argsVlue;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgsVlue() {
        return argsVlue;
    }

    public void setArgsVlue(Object[] argsVlue) {
        this.argsVlue = argsVlue;
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "className=" + className +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", argsVlue=" + Arrays.toString(argsVlue) +
                '}';
    }
}
