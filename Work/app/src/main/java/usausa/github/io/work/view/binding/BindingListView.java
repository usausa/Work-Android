package usausa.github.io.work.view.binding;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import usausa.github.io.work.R;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingListView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    private Disposable disposable;

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_list;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize(@NonNull final View view) {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }

        executing.set(true);

        disposable = Single.fromCallable(() -> getDataService().queryEntityList(10000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> executing.set(false), t-> executing.set(false));
    }

    @Override
    protected void onDispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }
}
