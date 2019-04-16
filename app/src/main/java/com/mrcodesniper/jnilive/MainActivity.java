package com.mrcodesniper.jnilive;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Native;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        NativeHelper nativeHelper = new NativeHelper();
//        Toast.makeText(this, nativeHelper.strFromJni(),Toast.LENGTH_SHORT).show();
//
//        Log.d("TAG",(nativeHelper.getNewPerson().getName()));

        Toast.makeText(this, nativeHelper.getNewPerson().getName(), Toast.LENGTH_SHORT).show();

        Log.d("xxx", Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public void onPatch(View view) {

        String oldfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bspatch/old.apk";
        String newfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bspatch/new.apk";
        String patchfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bspatch/app_patch.patch";

        int restlt = BsHelper.getInstance().bsPatch(oldfile, newfile, patchfile);
        if (restlt == 0) {
            Log.d("ywl5320", "合并成功");
            Toast.makeText(this, "合并成功", Toast.LENGTH_LONG).show();
        } else {
            Log.d("ywl5320", "合并失败");
            Toast.makeText(this, "合并失败", Toast.LENGTH_LONG).show();
        }
    }

}
