package shaolizhi.leanclouddemo;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * 由邵励治于2018/1/30创造.
 */

public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"TDJUy79LG3JzSqVw7Hj1emIr-gzGzoHsz","P1wJ6vfje0bVezeeio4eqCJ7");
        //调试日志 - 会把日志打印在DEBUG-LOG中
        //AVOSCloud.setDebugLogEnabled(true);
    }
}
