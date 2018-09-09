package usausa.github.io.work;

import java.util.Locale;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class DummyClient {

    private final Random random = new Random();

    private final PublishSubject<String> debugObservable = PublishSubject.create();

    private final PublishSubject<String> warningObservable = PublishSubject.create();

    public Observable<String> getDebugObservable() {
        return debugObservable;
    }
    public Observable<String> getWarningObservable() {
        return warningObservable;
    }

    public void execute(final Action action) {
        for (int i = 0; i < 5 + random.nextInt(5); i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Timber.e(e);
            }

            if (i % 3 == 0) {
                warningObservable.onNext(String.format(Locale.getDefault(), "Counter = [%d]", i));
            } else {
                debugObservable.onNext(String.format(Locale.getDefault(), "Counter = [%d]", i));
            }
        }

        if (Action.ACTION2.equals(action)) {
            throw new RuntimeException("Exception.");
        }
    }
}
