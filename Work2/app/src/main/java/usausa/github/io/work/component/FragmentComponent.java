package usausa.github.io.work.component;

import dagger.Subcomponent;
import usausa.github.io.work.view.AppViewBase;

@FragmentScope
@Subcomponent
public interface FragmentComponent {

    void inject(AppViewBase view);
}
