package com.example.kotlinplugin;

import android.util.Log;

/**
 * Package:      com.example.kotlinplugin
 * ClassName:    MyTest
 * Author:       hanyonggang
 * Date:         2021/12/28 11:57
 * Description:
 */
public class MyTest {
    private String tt = "00000004534";
    public void test(){
        String y = tt.replaceFirst("^0*","");
        Log.i("ddd","================"+y);
    }
}
