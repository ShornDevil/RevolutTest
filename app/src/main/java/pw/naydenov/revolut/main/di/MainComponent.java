package pw.naydenov.revolut.main.di;

import javax.inject.Singleton;

import dagger.Component;
import pw.naydenov.revolut.main.mvp.MainViewState;

@Component (modules = MainModule.class)
@Singleton
public interface MainComponent {

    void inject(MainViewState viewState);

}
