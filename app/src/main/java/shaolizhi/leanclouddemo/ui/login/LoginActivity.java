package shaolizhi.leanclouddemo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;
import shaolizhi.leanclouddemo.ui.main.MainActivity;
import shaolizhi.leanclouddemo.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    //login button click event
    private void clickLoginButton() {
        attemptLogin();
    }

    //register button click event
    private void clickRegisterButton() {
        startActivity(RegisterActivity.newIntent(this));
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void created(Bundle bundle) {
        //testLeanCloudAPI();
        initView();
        initListener();
        automaticLogin();
    }

    @Override
    protected void resumed() {
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLoginButton();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegisterButton();
            }
        });
    }

    private void initView() {
        usernameEditText = findViewById(R.id.login_act_textinputedittext1);
        passwordEditText = findViewById(R.id.login_act_textinputedittext2);
        loginButton = findViewById(R.id.login_act_button1);
        registerButton = findViewById(R.id.login_act_button2);
    }

    //------------------------------------------true music--------------------------------------------//
    private void automaticLogin() {
        if (AVUser.getCurrentUser() != null) {
            startActivity(MainActivity.newIntent(this));
            LoginActivity.this.finish();
        }
    }

    //尝试登录，成功则打开MainActivity
    private void attemptLogin() {
        usernameEditText.setError(null);
        passwordEditText.setError(null);

        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        Boolean isInputMistake = false;
        TextInputEditText inputMistakeEditText = null;

        if (!checkPasswordFormat(password)) {
            passwordEditText.setError(getString(R.string.login_act_string6));
            inputMistakeEditText = passwordEditText;
            isInputMistake = true;
        }

        if (checkUsernameFormat(username)) {
            usernameEditText.setError(getString(R.string.login_act_string5));
            inputMistakeEditText = usernameEditText;
            isInputMistake = true;
        }

        if (isInputMistake) {
            inputMistakeEditText.requestFocus();
        } else {
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        startActivity(new Intent(MainActivity.newIntent(LoginActivity.this)));
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //用户名检查：可以使用正则表达式
    private boolean checkUsernameFormat(String username) {
        return TextUtils.isEmpty(username);
    }

    //密码检查：可以使用正则表达式
    private boolean checkPasswordFormat(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
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

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, LoginActivity.class);
    }
}
