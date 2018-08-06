package usausa.github.io.work.view;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

public class Navigator {

    private static final String VIEW_TAG = "VIEW";

    private final FragmentManager manager;

    private final int containerViewId;

    private final ViewFactory viewFactory;

    public Fragment getCurrent() {
        return manager.findFragmentByTag(VIEW_TAG);
    }

    public Navigator(@NonNull final FragmentManager manager, @IdRes final int containerViewId, @NonNull final ViewFactory viewFactory) {
        this.manager = manager;
        this.containerViewId = containerViewId;
        this.viewFactory = viewFactory;
    }

    public void navigate(final int id) {
        Fragment fragment = viewFactory.create(id);

        Fragment current = getCurrent();
        if (current != null) {
            View view = current.getView();
            if (view != null) {
                disable(view);
            }
        }

        manager.beginTransaction().replace(containerViewId, fragment, VIEW_TAG).commit();
        manager.executePendingTransactions();
    }

    private static void disable(final View view) {
        view.setEnabled(false);
        view.setFocusable(false);

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                disable(viewGroup.getChildAt(i));
            }
        }
    }

    public interface ViewFactory {

        Fragment create(final int id);
    }
}
