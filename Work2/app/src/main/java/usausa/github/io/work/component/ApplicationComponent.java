package usausa.github.io.work.component;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {

    ActivityComponent newActivityComponent(ActivityModule module);
}
