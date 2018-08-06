package usausa.github.io.work.component;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;
import usausa.github.io.work.view.Navigator;

@Module
public class ActivityModule {

    private final FragmentActivity activity;

    private final int containerViewId;

    private final Navigator.ViewFactory viewFactory;

    public ActivityModule(@NonNull final FragmentActivity activity, @IdRes final int containerViewId, @NonNull final Navigator.ViewFactory viewFactory) {
        this.activity = activity;
        this.containerViewId = containerViewId;
        this.viewFactory = viewFactory;
    }

    @Provides
    @ActivityScope
    public Navigator providesNavigator() {
        return new Navigator(activity.getSupportFragmentManager(), containerViewId, viewFactory);
    }
}
