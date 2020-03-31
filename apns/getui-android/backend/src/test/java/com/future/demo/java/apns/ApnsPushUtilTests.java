package com.future.demo.java.apns;

import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.junit.Test;

/**
 * 个推推送消息到APP单元测试工具
 */
public class ApnsPushUtilTests {
    private final static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     *
     */
    @Test
    public void push() {
        String appId = System.getenv("appId");
        String appKey = System.getenv("appKey");
        String masterSecret = System.getenv("masterSecret");
        String deviceId = System.getenv("deviceId");
        // STEP1：获取应用基本信息
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容
        String title = "测试标题1";
        String content = "中文测试内容";
        style.setTitle(title);
        style.setText(content);
//        style.setLogo("push.png");  // 设置推送图标
        // STEP3：设置响铃、震动等推送效果
        style.setRing(true);  // 设置响铃
        style.setVibrate(true);  // 设置震动
        // STEP4：选择通知模板
        NotificationTemplate template = new NotificationTemplate();
        template.setNotifyid(1);
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setStyle(style);
        // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
        SingleMessage message = new SingleMessage();
        message.setData(template);
//            message.setOffline(true);
//            message.setOfflineExpireTime(1000 * 600);  // 时间单位为毫秒
        // STEP6：执行推送
//            IPushResult ret = push.pushMessageToApp(message);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(deviceId);
        push.pushMessageToSingle(message, target);
    }
}
