package usausa.github.io.work.view.binding;

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
import java.util.Locale;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemBindingAlternativeBinding;
import usausa.github.io.work.service.data.DataEntity;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingAlternativeView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableArrayList<DataEntity> list = new ObservableArrayList<>();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_alternative;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        list.addAll(getDataService().queryEntityList(20, 0));
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }

    @Override
    public void executeFunction4() {
        int id = list.size() + 1;
        DataEntity entity = new DataEntity();
        entity.setId(String.valueOf(id));
        entity.setName(String.format(Locale.getDefault(), "New--%d", id));
        list.add(0, entity);
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
            ItemBindingAlternativeBinding binding;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                binding = DataBindingUtil.inflate(inflater, R.layout.item_binding_alternative, parent, false);

                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ItemBindingAlternativeBinding)convertView.getTag();
            }

            binding.setItem(getItem(position));
            binding.setIndex(getCount() - position - 1);

            return binding.getRoot();
        }
    }

    @BindingAdapter("list_binding_alternative")
    public static void setList(final ListView listView, final List<DataEntity> objects) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }
}
