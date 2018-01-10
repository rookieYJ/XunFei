package com.example.yujie.xunfei.app;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by yujie on 2017/12/27.
 */

public class RookieApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5a4363ed");
    }
}
