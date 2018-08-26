package usausa.github.io.work.view.layout;

import android.databinding.ObservableBoolean;

import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class LayoutSubMenuView extends AppViewBase {

    public final ObservableBoolean submenu1 = new ObservableBoolean();
    public final ObservableBoolean submenu2 = new ObservableBoolean();
    public final ObservableBoolean submenu3 = new ObservableBoolean();

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
    public void executeFunction2() {
        submenu1.set(!submenu1.get());
        submenu2.set(false);
        submenu3.set(false);
    }

    @Override
    public void executeFunction3() {
        submenu1.set(false);
        submenu2.set(!submenu2.get());
        submenu3.set(false);
    }

    @Override
    public void executeFunction4() {
        submenu1.set(false);
        submenu2.set(false);
        submenu3.set(!submenu3.get());
    }
}
