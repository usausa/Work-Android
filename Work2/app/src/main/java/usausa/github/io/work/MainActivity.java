package usausa.github.io.work;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import usausa.github.io.work.component.ActivityComponent;
import usausa.github.io.work.component.ActivityModule;
import usausa.github.io.work.view.helper.Navigator;
import usausa.github.io.work.view.ViewId;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getComponent().inject(this);

        // Navigate
        navigator.navigate(ViewId.MENU);
    }
}
