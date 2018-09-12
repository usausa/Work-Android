package usausa.github.io.work.view.layout;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemLayoutExpandBinding;
import usausa.github.io.work.service.data.DataEntity;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class LayoutExpandView extends AppViewBase {

    public final ObservableBoolean showInput = new ObservableBoolean(true);
    public final ObservableBoolean showList = new ObservableBoolean(true);

    public final ObservableArrayList<DataEntity> list = new ObservableArrayList<>();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_layout_expand;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        list.addAll(getDataService().queryEntityList(20));
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
        showInput.set(true);
        showList.set(true);
    }

    @Override
    public void executeFunction3() {
        showInput.set(true);
        showList.set(false);
    }

    @Override
    public void executeFunction4() {
        showInput.set(false);
        showList.set(true);
    }

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<DataEntity> {

        public ListViewAdaptor(@NonNull final Context context, final List<DataEntity> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
            ItemLayoutExpandBinding binding;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                binding = DataBindingUtil.inflate(inflater, R.layout.item_layout_expand, parent, false);

                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ItemLayoutExpandBinding)convertView.getTag();
            }

            binding.setItem(getItem(position));

            return binding.getRoot();
        }
    }

    @BindingAdapter("list_layout_expand")
    public static void setList(final ListView listView, final List<DataEntity> objects) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }

}
