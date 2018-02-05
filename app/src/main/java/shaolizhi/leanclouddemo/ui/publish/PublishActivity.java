package shaolizhi.leanclouddemo.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;

public class PublishActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void created(Bundle bundle) {
        setActionBarTitle();
    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.publish_act_string6);
        }
    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, PublishActivity.class);
    }
}
