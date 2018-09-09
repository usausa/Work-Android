package usausa.github.io.work.component;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import usausa.github.io.work.DummyClient;
import usausa.github.io.work.model.TraceStorage;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public TraceStorage providesTraceStorage() {
        return new TraceStorage();
    }

    @Provides
    @Singleton
    public DummyClient providesDummyClient() {
        return new DummyClient();
    }
}
