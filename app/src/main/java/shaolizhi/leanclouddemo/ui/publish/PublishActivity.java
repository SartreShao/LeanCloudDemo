package shaolizhi.leanclouddemo.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;

public class PublishActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void created(Bundle bundle) {

    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, PublishActivity.class);
    }
}
