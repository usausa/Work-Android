package usausa.github.io.work.component;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import usausa.github.io.work.service.DataService;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public DataService providesDataService() {
        return new DataService();
    }
}
