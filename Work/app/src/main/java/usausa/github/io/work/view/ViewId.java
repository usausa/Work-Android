package usausa.github.io.work.view;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import usausa.github.io.work.model.Factory;
import usausa.github.io.work.view.binding.BindingAlternativeView;
import usausa.github.io.work.view.binding.BindingClickableView;
import usausa.github.io.work.view.binding.BindingGroupView;
import usausa.github.io.work.view.binding.BindingSelectView;
import usausa.github.io.work.view.binding.BindingMiscView;
import usausa.github.io.work.view.helper.Navigator;
import usausa.github.io.work.view.layout.LayoutExpandView;
import usausa.github.io.work.view.layout.LayoutKeyboardView;
import usausa.github.io.work.view.layout.LayoutNestedView;
import usausa.github.io.work.view.layout.LayoutSubMenuView;
import usausa.github.io.work.view.misc.MiscTransferReceiveView;
import usausa.github.io.work.view.misc.MiscTransferSendView;

public final class ViewId {

    public static final int MENU = 1;

    public static final int BINDING_MISC = 101;
    public static final int BINDING_SELECT = 102;
    public static final int BINDING_ALTERNATIVE = 103;
    public static final int BINDING_CLICKABLE = 104;
    public static final int BINDING_GROUP = 105;

    public static final int LAYOUT_SUB_MENU = 201;
    public static final int LAYOUT_KEYBOARD = 202;
    public static final int LAYOUT_EXPAND = 203;
    public static final int LAYOUT_NESTED = 204;

    public static final int MISC_TRANSFER_SEND = 301;
    public static final int MISC_TRANSFER_RECEIVE = 302;

    private static final Map<Integer, Factory<Fragment>> factories = new HashMap<>();

    static {
        factories.put(MENU, MenuView::new);
        factories.put(BINDING_MISC, BindingMiscView::new);
        factories.put(BINDING_SELECT, BindingSelectView::new);
        factories.put(BINDING_ALTERNATIVE, BindingAlternativeView::new);
        factories.put(BINDING_CLICKABLE, BindingClickableView::new);
        factories.put(BINDING_GROUP, BindingGroupView::new);
        factories.put(LAYOUT_SUB_MENU, LayoutSubMenuView::new);
        factories.put(LAYOUT_KEYBOARD, LayoutKeyboardView::new);
        factories.put(LAYOUT_EXPAND, LayoutExpandView::new);
        factories.put(LAYOUT_NESTED, LayoutNestedView::new);
        factories.put(MISC_TRANSFER_SEND, MiscTransferSendView::new);
        factories.put(MISC_TRANSFER_RECEIVE, MiscTransferReceiveView::new);
    }

    public static Navigator.ViewFactory createFactory() {
        return id -> factories.get(id).get();
    }

    private ViewId() {
    }
}
