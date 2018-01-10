package com.example.yujie.xunfei;

/**
 * Created by yujie on 2017/12/28.
 */

public class Api {
    /**
     * 1、官网注册账号，创建应用，生成APPID，根据自己的应用和需求下载所需的组合SDK
       2、将下载好的library中 libs文件夹下的 全部粘贴到自己module的libs文件夹里
          accets整个文件夹粘贴到main下面
       3、Application注册讯飞 SpeechUtility.createUtility(this, SpeechConstant.APPID +"=自己申请的APPID");
       4、官方文档有 识别语音 和 生成语音 的代码
          识别语音返回结果为Json格式（Gson解析），而且他不是按照整句话返回，一般都是一个字符一个字符，最后为标点
          这时候就需要创建一个容器（List集合）来存储这些字符
          因为根据需求需要返回整句话而不是一个字符，这时候监听事件onResult回调方法里的第二个参数就派上用场了，
          当b返回true的时候代表会话结束，在这个时候我们遍历容器，将所有的字符拼接起来返回即可
     */
}
