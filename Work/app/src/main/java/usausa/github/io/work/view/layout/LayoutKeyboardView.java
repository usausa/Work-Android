package usausa.github.io.work.view.layout;

import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class LayoutKeyboardView extends AppViewBase {

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_layout_keyboard;
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }
}
