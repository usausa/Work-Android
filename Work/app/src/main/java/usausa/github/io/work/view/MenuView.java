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

    public void onClickBindingList() {
        getNavigator().navigate(ViewId.BINDING_LIST);
    }

    public void onClickBindingMisc() {
        getNavigator().navigate(ViewId.BINDING_MISC);
    }

    public void onClickLayoutMenu() {
        getNavigator().navigate(ViewId.LAYOUT_MENU);
    }

    public void onClickLayoutKeyboard() {
        getNavigator().navigate(ViewId.LAYOUT_KEYBOARD);
    }

    public void onClickLayoutExpand() {
        getNavigator().navigate(ViewId.LAYOUT_EXPAND);
    }
}
