package shaolizhi.leanclouddemo.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;
import shaolizhi.leanclouddemo.ui.main.MainActivity;

public class RegisterActivity extends BaseActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button registerButton;

    //register button click event
    private void clickRegisterButton() {
        attemptRegister();
    }

    private void attemptRegister() {
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
            AVUser user = new AVUser();
            user.setUsername(username);
            user.setPassword(password);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        startActivity(MainActivity.newIntent(RegisterActivity.this));
                        RegisterActivity.this.finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void created(Bundle bundle) {
        setActionBarTitle();
        initView();
        initListener();
    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.register_act_string4);
        }
    }

    private void initListener() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegisterButton();
            }
        });
    }

    private void initView() {
        usernameEditText = findViewById(R.id.register_act_textinputedittext1);
        passwordEditText = findViewById(R.id.register_act_textinputedittext2);
        registerButton = findViewById(R.id.register_act_button1);
    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, RegisterActivity.class);
    }
}
