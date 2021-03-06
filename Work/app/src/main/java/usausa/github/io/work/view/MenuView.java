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

    public void onClickBindingAlternative() {
        getNavigator().navigate(ViewId.BINDING_ALTERNATIVE);
    }

    public void onClickBindingClickable() {
        getNavigator().navigate(ViewId.BINDING_CLICKABLE);
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

    public void onClickLayoutNested() {
        getNavigator().navigate(ViewId.LAYOUT_NESTED);
    }

    public void onClickMiscTransferSend() {
        getNavigator().navigate(ViewId.MISC_TRANSFER_SEND);
    }

    public void onClickMiscTransferReceive() {
        getNavigator().navigate(ViewId.MISC_TRANSFER_RECEIVE);
    }
}
