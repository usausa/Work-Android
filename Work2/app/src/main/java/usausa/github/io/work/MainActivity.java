package usausa.github.io.work;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import usausa.github.io.work.component.ActivityComponent;
import usausa.github.io.work.component.ActivityModule;
import usausa.github.io.work.model.EventType;
import usausa.github.io.work.model.TraceStorage;
import usausa.github.io.work.view.Navigator;
import usausa.github.io.work.view.ViewId;

public class MainActivity extends AppCompatActivity {

    @Inject
    DummyClient client;

    @Inject
    TraceStorage traceStorage;

    @Inject
    Navigator navigator;

    private ActivityComponent component;

    @NonNull
    public ActivityComponent getComponent() {
        if (component == null) {
            component = ((WorkApplication)getApplication()).getComponent().newActivityComponent(new ActivityModule(this, R.id.main_content, ViewId.createFactory()));
        }
        return component;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getComponent().inject(this);

        // Storage
        client.getDebugObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> traceStorage.AddEntry(EventType.DEBUG, message));
        client.getWarningObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> traceStorage.AddEntry(EventType.WARN, message));

        // Navigate
        navigator.navigate(ViewId.MENU);
    }
}
