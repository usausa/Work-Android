package usausa.github.io.work.view.layout;

import android.databinding.ObservableBoolean;

import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class LayoutSubMenuView extends AppViewBase {

    public final ObservableBoolean submenu = new ObservableBoolean();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_layout_sub_menu;
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }

    @Override
    public void executeFunction3() {
        submenu.set(!submenu.get());
    }
}
