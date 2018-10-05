package usausa.github.io.work.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import usausa.github.io.work.service.data.DataService;
import usausa.github.io.work.service.transfer.TransferService;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public DataService providesDataService() {
        return new DataService();
    }

    @Provides
    @Singleton
    public TransferService providesTransferService(final Application application) {
        return new TransferService(application);
    }
}
