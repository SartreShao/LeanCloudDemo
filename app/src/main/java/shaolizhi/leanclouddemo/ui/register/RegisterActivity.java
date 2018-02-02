package shaolizhi.leanclouddemo.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void created(Bundle bundle) {

    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, RegisterActivity.class);
    }
}
