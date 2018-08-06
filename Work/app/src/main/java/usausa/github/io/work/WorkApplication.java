package usausa.github.io.work;

import android.app.Application;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;

import timber.log.Timber;
import usausa.github.io.work.component.ApplicationComponent;
import usausa.github.io.work.component.ApplicationModule;
import usausa.github.io.work.component.DaggerApplicationComponent;
import usausa.github.io.work.component.ServiceModule;

public class WorkApplication extends Application {

    private ApplicationComponent component;

    @NonNull
    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Timber.plant for crash report
        Timber.plant(new Timber.DebugTree());

        Stetho.initializeWithDefaults(this);

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .serviceModule(new ServiceModule())
                .build();
    }
}
