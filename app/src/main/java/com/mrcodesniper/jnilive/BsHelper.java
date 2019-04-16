package com.mrcodesniper.jnilive;

public class BsHelper {

    private static BsHelper instance = new BsHelper();

    private BsHelper() {
    }

    public static BsHelper getInstance() {
        return instance;
    }


    static
    {
        System.loadLibrary("BsPatchYwl5320");
    }




    public native int bsPatch(String oldfile, String newfile, String patchfile);



}
