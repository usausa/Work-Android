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

    public void executeList() {
        getNavigator().navigate(ViewId.BINDING_LIST);
    }

    public void executeBinding() {
        getNavigator().navigate(ViewId.BINDING_MISC);
    }
}
