package usausa.github.io.work.view;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import usausa.github.io.work.Action;
import usausa.github.io.work.Mode;
import usausa.github.io.work.R;
import usausa.github.io.work.model.EventType;
import usausa.github.io.work.model.TraceEntry;

public class MenuView extends AppViewBase {

    public final ObservableBoolean executing = new ObservableBoolean();

    public final ObservableBoolean showMenu = new ObservableBoolean();

    public final ObservableField<Mode> currentMode = new ObservableField<>();

    public ObservableList<TraceEntry> list;

    private Disposable disposable;

    //--------------------------------------------------------------------------------
    // Layout
    //--------------------------------------------------------------------------------

    @Override
    protected int getViewId() {
        return R.layout.view_menu;
    }

    //--------------------------------------------------------------------------------
    // Initialize
    //--------------------------------------------------------------------------------

    @Override
    protected void onInitialize() {
        list = getTraceStorage().getEntries();

        // TODO client


        updateMode(Mode.MODE1);
    }

    @Override
    protected void onDispose() {
        clearDisposable();
    }

    //--------------------------------------------------------------------------------
    // Event
    //--------------------------------------------------------------------------------

    private void clearDisposable() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    public void showMenu() {
        showMenu.set(!showMenu.get());
    }

    public void updateMode(final Mode mode) {
        showMenu.set(false);
        currentMode.set(mode);
    }

    public void executeClear() {
        showMenu.set(false);
        list.clear();
    }

    public void executeAction(final Action action) {
        showMenu.set(false);
        executing.set(true);

        getTraceStorage().AddEntry(EventType.INFO, "Start.");

        clearDisposable();
        disposable = Completable.fromAction(() -> getClient().execute(action))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            getTraceStorage().AddEntry(EventType.INFO, "Complete.");
                            executing.set(false);
                        },
                        t -> {
                            getTraceStorage().AddEntry(EventType.ERROR, t.toString());
                            executing.set(false);
                        });
    }
}
