package usausa.github.io.work.view;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import usausa.github.io.work.view.binding.BindingGroupView;
import usausa.github.io.work.view.binding.BindingSelectView;
import usausa.github.io.work.view.binding.BindingMiscView;
import usausa.github.io.work.view.helper.Navigator;
import usausa.github.io.work.view.layout.LayoutExpandView;
import usausa.github.io.work.view.layout.LayoutKeyboardView;
import usausa.github.io.work.view.layout.LayoutSubMenuView;

public final class ViewId {

    @FunctionalInterface
    private interface Factory<T> {

        T get();
    }

    public static final int MENU = 1;

    public static final int BINDING_MISC = 101;
    public static final int BINDING_SELECT = 102;
    public static final int BINDING_GROUP = 103;

    public static final int LAYOUT_SUB_MENU = 201;
    public static final int LAYOUT_KEYBOARD = 202;
    public static final int LAYOUT_EXPAND = 203;

    private static final Map<Integer, Factory<Fragment>> factories = new HashMap<>();

    static {
        factories.put(MENU, MenuView::new);
        factories.put(BINDING_MISC, BindingMiscView::new);
        factories.put(BINDING_SELECT, BindingSelectView::new);
        factories.put(BINDING_GROUP, BindingGroupView::new);
        factories.put(LAYOUT_SUB_MENU, LayoutSubMenuView::new);
        factories.put(LAYOUT_KEYBOARD, LayoutKeyboardView::new);
        factories.put(LAYOUT_EXPAND, LayoutExpandView::new);
    }

    public static Navigator.ViewFactory createFactory() {
        return id -> factories.get(id).get();
    }

    private ViewId() {
    }
}
