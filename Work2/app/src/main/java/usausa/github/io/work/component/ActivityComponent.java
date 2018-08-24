package usausa.github.io.work.component;

import dagger.Subcomponent;
import usausa.github.io.work.MainActivity;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    FragmentComponent newFragmentComponent();
}
