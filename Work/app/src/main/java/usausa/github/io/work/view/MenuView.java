package usausa.github.io.work.view;

import usausa.github.io.work.R;

public class MenuView extends AppViewBase {

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_menu;
    }

    @Override
    protected boolean isDisplayUser() {
        return false;
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    public void onClickBindingMisc() {
        getNavigator().navigate(ViewId.BINDING_MISC);
    }

    public void onClickBindingSelect() {
        getNavigator().navigate(ViewId.BINDING_SELECT);
    }

    public void onClickBindingGroup() {
        getNavigator().navigate(ViewId.BINDING_GROUP);
    }

    public void onClickLayoutSubMenu() {
        getNavigator().navigate(ViewId.LAYOUT_SUB_MENU);
    }

    public void onClickLayoutKeyboard() {
        getNavigator().navigate(ViewId.LAYOUT_KEYBOARD);
    }

    public void onClickLayoutExpand() {
        getNavigator().navigate(ViewId.LAYOUT_EXPAND);
    }

    public void onClickMiscTransfer() {
        getNavigator().navigate(ViewId.MISC_TRANSFER);
    }
}
