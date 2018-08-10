package usausa.github.io.work.view;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import usausa.github.io.work.view.binding.BindingListView;
import usausa.github.io.work.view.binding.BindingMiscView;
import usausa.github.io.work.view.helper.Navigator;
import usausa.github.io.work.view.layout.LayoutMenuView;

public final class ViewId {

    @FunctionalInterface
    private interface Factory<T> {

        T get();
    }

    public static final int MENU = 1;

    public static final int BINDING_LIST = 101;
    public static final int BINDING_MISC = 102;

    public static final int LAYOUT_MENU = 201;

    private static final Map<Integer, Factory<Fragment>> factories = new HashMap<>();

    static {
        factories.put(MENU, MenuView::new);
        factories.put(BINDING_LIST, BindingListView::new);
        factories.put(BINDING_MISC, BindingMiscView::new);
        factories.put(LAYOUT_MENU, LayoutMenuView::new);
    }

    public static Navigator.ViewFactory createFactory() {
        return id -> factories.get(id).get();
    }

    private ViewId() {
    }
}
