package shaolizhi.leanclouddemo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.List;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;
import shaolizhi.leanclouddemo.ui.login.LoginActivity;
import shaolizhi.leanclouddemo.ui.publish.PublishActivity;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private MainRecyclerAdapter adapter;
    private List<AVObject> listData = new ArrayList<>();

    //floating-action-button click event
    private void clickFloatingActionButton() {
        startActivity(PublishActivity.newIntent(this));
    }

    //logout click event
    private void clickLogout() {
        logout();
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void created(Bundle bundle) {
        initView();
        initListener();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
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
        adapter = new MainRecyclerAdapter(this, listData);
    }

    private void logout() {
        if (AVUser.getCurrentUser() != null) {
            AVUser.logOut();
            startActivity(LoginActivity.newIntent(this));
            MainActivity.this.finish();
        }
    }

    @Override
    protected void resumed() {
        listData.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Product");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    listData.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
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
