package shaolizhi.leanclouddemo.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 由邵励治于2018/2/1创造.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected abstract
    @LayoutRes
    int layoutId();

    protected abstract void created(Bundle bundle);

    protected abstract void resumed();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        created(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumed();
    }
}
