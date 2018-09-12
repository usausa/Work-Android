package usausa.github.io.work;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import timber.log.Timber;
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

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            View contentView = getWindow().getDecorView();
            Rect rect = new Rect();
            contentView.getWindowVisibleDisplayFrame(rect);
            int screenHeight = contentView.getRootView().getHeight();
            int keypadHeight = screenHeight - rect.bottom;
            boolean show = keypadHeight > screenHeight * 0.15;

            Timber.d("****** screenHeight = %d, keypadHeight = %d, show = %s", screenHeight, keypadHeight, String.valueOf(show));
        });

        // Navigate
        navigator.navigate(ViewId.MENU);
    }
}
