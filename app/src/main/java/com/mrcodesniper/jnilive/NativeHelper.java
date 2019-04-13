package com.mrcodesniper.jnilive;

public class NativeHelper {

    static {
        System.loadLibrary("data");
        System.loadLibrary("native-lib");
        //当Android的VM(Virtual Machine)执行到C组件(即*so档)里的System.loadLibrary()函数时，
        //首先会去执行C组件里的JNI_OnLoad()函数。
    }


    public native String strFromJni();

    public native int add(int a,int b);

    public native Person getNewPerson();

}
