package usausa.github.io.work.view.shared;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.HeaderBinding;

public class HeaderFragment extends Fragment {

    public final ObservableBoolean userIdEnable = new ObservableBoolean();

    public final ObservableField<String> userId = new ObservableField<>();

    public final ObservableField<String> terminalNo = new ObservableField<>();

    @CallSuper
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        HeaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.header, container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userId.set("999999");
        terminalNo.set("11111111");
    }
}
