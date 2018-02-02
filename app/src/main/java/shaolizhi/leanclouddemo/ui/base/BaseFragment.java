package shaolizhi.leanclouddemo.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 由邵励治于2018/2/1创造.
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    protected View mFragment;

    protected abstract
    @LayoutRes
    int layoutId();

    protected abstract void created(Bundle bundle);

    protected abstract void resumed();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragment = inflater.inflate(layoutId(), container, false);
        return mFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        created(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        resumed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }
}
