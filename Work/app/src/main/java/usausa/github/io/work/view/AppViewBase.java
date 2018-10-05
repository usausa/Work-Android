package usausa.github.io.work.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import usausa.github.io.work.BR;
import usausa.github.io.work.MainActivity;
import usausa.github.io.work.component.FragmentComponent;
import usausa.github.io.work.service.data.DataService;
import usausa.github.io.work.service.transfer.TransferService;
import usausa.github.io.work.view.helper.Navigator;

public abstract class AppViewBase extends Fragment {

    public final ObservableBoolean userIdEnable = new ObservableBoolean(true);

    public final ObservableField<String> userId = new ObservableField<>();

    public final ObservableField<String> terminalNo = new ObservableField<>();

    private ViewDataBinding binding;

    private CompositeDisposable disposables;

    @Inject
    Navigator navigator;

    @Inject
    DataService dataService;

    @Inject
    TransferService transferService;

    private FragmentComponent component;

    @NonNull
    private FragmentComponent getComponent() {
        if (component == null) {
            component = ((MainActivity)Objects.requireNonNull(getActivity())).getComponent().newFragmentComponent();
        }
        return component;
    }

    @NonNull
    protected Navigator getNavigator() {
        return navigator;
    }

    @NonNull
    protected DataService getDataService() {
        return dataService;
    }

    @NonNull
    protected TransferService getTransferService() {
        return transferService;
    }

    // --------------------------------------------------------------------------------
    // Lifecycle
    // --------------------------------------------------------------------------------

    @CallSuper
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getViewId(), container, false);
        binding.setVariable(BR.view, this);
        return binding.getRoot();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO
        userIdEnable.set(isDisplayUser());
        if (userIdEnable.get()) {
            userId.set("999999");
        }
        terminalNo.set("11111111");

        onInitialize();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        onDispose();

        if (disposables != null) {
            disposables.dispose();
        }

        binding.unbind();

        super.onDestroyView();
    }

    protected abstract int getViewId();

    protected boolean isDisplayUser() {
        return true;
    }


    protected void onInitialize() {
    }

    protected void onDispose() {
    }

    protected void addDisposable(final Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }

        disposables.add(disposable);
    }

    // --------------------------------------------------------------------------------
    // Functions
    // --------------------------------------------------------------------------------

    public void executeFunction1() {
    }

    public void executeFunction2() {
    }

    public void executeFunction3() {
    }

    public void executeFunction4() {
    }
}
