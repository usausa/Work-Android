package usausa.github.io.work.service.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class DataService {

    public List<DataEntity> queryEntityList(final int count, final int wait) {
        List<DataEntity> list = new ArrayList<>(count);

        for (int i = 1; i <= count; i++) {
            DataEntity entity = new DataEntity();
            entity.setId(String.valueOf(i));
            entity.setName(String.format(Locale.getDefault(), "Data-%d", i));
            list.add(0, entity);
        }

        // Dummy
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            Timber.e(e);
        }

        return list;
    }
}
