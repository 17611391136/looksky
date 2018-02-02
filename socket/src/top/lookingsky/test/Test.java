package top.lookingsky.test;


import java.io.File;

/**
 * Created by 李明 on 2018/1/31.
 */
public class Test {

    private static int j=0;
    private static boolean methodB(int k){
        j+=k;
        return true;
    }
    private static void methodA(int i){
        boolean b;
        b=i<10|methodB(4);
        b=i<10||methodB(8);

    }

    public static void main(String[] args) {

    }
}
