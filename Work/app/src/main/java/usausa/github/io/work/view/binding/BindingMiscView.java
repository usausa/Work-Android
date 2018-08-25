package usausa.github.io.work.view.binding;

import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingMiscView extends AppViewBase {

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_misc;
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }
}
