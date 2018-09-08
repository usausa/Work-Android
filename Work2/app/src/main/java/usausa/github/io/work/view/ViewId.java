package usausa.github.io.work.view;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import usausa.github.io.work.view.helper.Navigator;

public final class ViewId {

    @FunctionalInterface
    private interface Factory<T> {

        T get();
    }

    public static final int MENU = 1;

    private static final Map<Integer, Factory<Fragment>> factories = new HashMap<>();

    static {
        factories.put(MENU, MenuView::new);
    }

    public static Navigator.ViewFactory createFactory() {
        return id -> factories.get(id).get();
    }

    private ViewId() {
    }
}
