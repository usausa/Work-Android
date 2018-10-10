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

import java.util.Arrays;
import java.util.List;

import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemBindingGroupBinding;
import usausa.github.io.work.model.GroupingItem;
import usausa.github.io.work.service.data.GroupDataEntity;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingGroupView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableArrayList<GroupingItem<GroupDataEntity>> list = new ObservableArrayList<>();

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_binding_group;
    }

    //--------------------------------------------------------------------------------
    // Function
    //--------------------------------------------------------------------------------

    @Override
    public void executeFunction1() {
        getNavigator().navigate(ViewId.MENU);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeFunction4() {
        GroupDataEntity dataA1 = new GroupDataEntity();
        dataA1.setGroup("A");
        dataA1.setName("A-1");
        dataA1.setOption("1");
        GroupDataEntity dataA2 = new GroupDataEntity();
        dataA2.setGroup("A");
        dataA2.setName("A-2");
        dataA2.setOption("2");
        GroupDataEntity dataB1 = new GroupDataEntity();
        dataB1.setGroup("B");
        dataB1.setName("B-1");

        list.add(GroupingItem.<GroupDataEntity>MakeHeader(Arrays.asList(GroupingItem.MakeChild(dataA1), GroupingItem.MakeChild(dataA2))));
        list.add(GroupingItem.MakeSingle(dataB1));
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    public void selectList(final int position) {
        GroupingItem<GroupDataEntity> item = list.get(position);
        if (item.isHeader()) {
            item.setExpanded(!item.isExpanded());
            if (item.isExpanded()) {
                list.addAll(position + 1, item.getChildren());
            } else {
                list.subList(position + 1, position + 1 + item.getChildren().size()).clear();
            }
        } else {
            item.setSelected(!item.isSelected());
        }
    }

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<GroupingItem<GroupDataEntity>> {

        public ListViewAdaptor(@NonNull final Context context, final List<GroupingItem<GroupDataEntity>> objects) {
            super(context, 0, objects);
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
            ItemBindingGroupBinding binding;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                binding = DataBindingUtil.inflate(inflater, R.layout.item_binding_group, parent, false);

                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ItemBindingGroupBinding) convertView.getTag();
            }

            binding.setItem(getItem(position));
            return binding.getRoot();
        }
    }

    @BindingAdapter("list_binding_group")
    public static void setList(final ListView listView, final List<GroupingItem<GroupDataEntity>> objects) {
        ListViewAdaptor adaptor = (ListViewAdaptor)listView.getAdapter();
        if (adaptor == null) {
            adaptor = new ListViewAdaptor(listView.getContext(), objects);
            listView.setAdapter(adaptor);
        }

        adaptor.notifyDataSetChanged();
    }
}
