package shaolizhi.leanclouddemo.ui.login;

import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void created(Bundle bundle) {
//        testLeanCloudAPI();

    }

    private void testLeanCloudAPI() {
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                }
            }
        });
    }

    @Override
    protected void resumed() {

    }


}
