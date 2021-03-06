package usausa.github.io.work.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
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
import usausa.github.io.work.DummyClient;
import usausa.github.io.work.MainActivity;
import usausa.github.io.work.component.FragmentComponent;
import usausa.github.io.work.model.TraceStorage;

public abstract class AppViewBase extends Fragment {

    private ViewDataBinding binding;

    private CompositeDisposable disposables;

    @Inject
    DummyClient client;

    @Inject
    TraceStorage traceStorage;

    @Inject
    Navigator navigator;

    private FragmentComponent component;

    @NonNull
    private FragmentComponent getComponent() {
        if (component == null) {
            component = ((MainActivity)Objects.requireNonNull(getActivity())).getComponent().newFragmentComponent();
        }
        return component;
    }

    @NonNull
    public DummyClient getClient() {
        return client;
    }

    @NonNull
    public TraceStorage getTraceStorage() {
        return traceStorage;
    }

    @NonNull
    protected Navigator getNavigator() {
        return navigator;
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
}
