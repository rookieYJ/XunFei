package com.example.yujie.xunfei.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yujie.xunfei.R;
import com.example.yujie.xunfei.bean.ListenerBean;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button listener;
    private Button compound;
    private List<ListenerBean.WsBean> wsBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        listener = (Button) findViewById(R.id.listener);

        listener.setOnClickListener(this);
        compound = (Button) findViewById(R.id.compound);
        compound.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listener:
                wsBeans.clear();
                RecognizerDialog dialog = new RecognizerDialog(this, null);

                dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

                dialog.setListener(new RecognizerDialogListener() {
                    @Override
                    public void onResult(RecognizerResult recognizerResult, boolean b) {
                        Log.d("MainActivity", recognizerResult.getResultString());

                        Gson gson = new Gson();
                        ListenerBean listenerBean = gson.fromJson(recognizerResult.getResultString(), ListenerBean.class);
                        wsBeans.addAll(listenerBean.getWs());
                        if(b){
                            StringBuilder builder = new StringBuilder();
                            for (ListenerBean.WsBean wsBean : wsBeans) {
                                builder.append(wsBean.getCw().get(0).getW());
                            }
                            String anwser = "对不起，没听清楚";
                            if(builder.toString().contains("你好")){
                                anwser = "你好";
                            }else if(builder.toString().contains("美女")){
                                String[] anwsers = {"500块钱，陪你玩一宿", "我已经帮您安排好了", "你怎么这么色啊"};
                                Random random = new Random();
                                anwser = anwsers[random.nextInt(3)];
                            }else if(builder.toString().contains("最美")){
                                anwser = "当然是刘欣茹啊，这还用问";
                            }
                            talk(anwser);
                        }
                    }

                    @Override
                    public void onError(SpeechError speechError) {

                    }
                });
                dialog.show();
                break;
            case R.id.compound:
                talk("科大讯飞");

                break;
        }
    }

    public void talk(String text){
        //1.创建SpeechSynthesizer对象, 第一个参数上下文,第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端,这些功能用到讯飞服务器,所以要有网络.
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        //3.开始合成,第一个参数就是转成声音的文字,可以自定义   第二个参数是合成监听器对象.我们不需要对声音有什么特殊处理,就传null
        mTts.startSpeaking(text,null);
    }

}
