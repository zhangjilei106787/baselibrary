package base.zjl.com.baselibrary.login.mvvm;

import android.app.Activity;
import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import org.simple.eventbus.EventBus;

import base.zjl.com.baselibrary.login.sentor.MyOrientationListener;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: zjl on 2018-8-6.
 * Class: 全局需要根据生命周期配置的对象
 */
public class ActivityconfigurationObserver implements DefaultLifecycleObserver {
    MyOrientationListener listener;
    private Activity context;

    public ActivityconfigurationObserver(Activity context) {
        this.context = context;
    }

    //极光推送 等需要根据生命周期操作的都可以加入进来
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        this.listener = new MyOrientationListener(context);
        EventBus.getDefault().register((Activity) context);

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Lifecycle.State currentState = owner.getLifecycle().getCurrentState();
        if (!currentState.equals(Lifecycle.State.DESTROYED)) {
            listener.start();
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Lifecycle.State currentState = owner.getLifecycle().getCurrentState();
        listener.stop();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().unregister((Activity)context);
    }
}
