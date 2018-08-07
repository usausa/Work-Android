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
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import usausa.github.io.work.R;
import usausa.github.io.work.databinding.ItemBindingListBinding;
import usausa.github.io.work.model.SelectedItem;
import usausa.github.io.work.service.DataEntity;
import usausa.github.io.work.view.AppViewBase;
import usausa.github.io.work.view.ViewId;

public class BindingListView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableArrayList<SelectedItem<DataEntity>> list = new ObservableArrayList<>();

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
        // TODO
        ListView listView = view.findViewById(R.id.list);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            SelectedItem<DataEntity> item = list.get(position);
            item.setSelected(!item.isSelected());
        });

        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }

        executing.set(true);

        disposable = Single.fromCallable(() -> getDataService().queryEntityList(100))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    executing.set(false);

                    list.addAll(Stream.of(result).map(SelectedItem::new).collect(Collectors.toList()));
                }, t-> {
                    executing.set(false);

                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                });
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

    public void executeFunction4() {
        DataEntity entity = new DataEntity();
        entity.setId(String.valueOf(list.size()));
        entity.setName("Test" + String.valueOf(list.size()));
        SelectedItem<DataEntity> item = new SelectedItem<>(entity);

        list.add(0, item);
    }

    //--------------------------------------------------------------------------------
    // Adaptor
    //--------------------------------------------------------------------------------

    private static final class ListViewAdaptor extends ArrayAdapter<SelectedItem<DataEntity>> {

        public ListViewAdaptor(@NonNull final Context context, final List<SelectedItem<DataEntity>> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
            ItemBindingListBinding binding;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                binding = DataBindingUtil.inflate(inflater, R.layout.item_binding_list, parent, false);

                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ItemBindingListBinding)convertView.getTag();
            }

            binding.setItem(getItem(position));

            return binding.getRoot();
        }
    }

    @BindingAdapter("android:list")
    public static void setList(ListView listView, List<SelectedItem<DataEntity>> objects) {
        if (listView.getAdapter() == null) {
            ListViewAdaptor adapter = new ListViewAdaptor(listView.getContext(), objects);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
        }
    }
}
