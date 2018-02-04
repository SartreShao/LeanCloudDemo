package shaolizhi.leanclouddemo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVUser;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;
import shaolizhi.leanclouddemo.ui.login.LoginActivity;
import shaolizhi.leanclouddemo.ui.publish.PublishActivity;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    //floating-action-button click event
    private void clickFloatingActionButton() {
        startActivity(PublishActivity.newIntent(this));
    }

    //logout click event
    private void clickLogout() {
        logout();
    }

    private void logout() {
        if (AVUser.getCurrentUser() != null) {
            AVUser.logOut();
            startActivity(LoginActivity.newIntent(this));
            MainActivity.this.finish();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void created(Bundle bundle) {
        initView();
        initListener();
    }

    private void initListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFloatingActionButton();
            }
        });
    }


    private void initView() {
        recyclerView = findViewById(R.id.main_act_recyclerview);
        floatingActionButton = findViewById(R.id.main_act_floatingactionbutton);
    }

    @Override
    protected void resumed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_act_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.main_act_logout:
                clickLogout();
                break;
        }
        return true;
    }


    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MainActivity.class);
    }
}
