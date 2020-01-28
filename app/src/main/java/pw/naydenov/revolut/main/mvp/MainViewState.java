package pw.naydenov.revolut.main.mvp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import pw.naydenov.revolut.main.di.MainComponent;
import pw.naydenov.revolut.main.domain.CurrenciesAdapter;

public class MainViewState extends ViewModel implements MainContract.View, MainContract.Presenter {

    private MutableLiveData<Boolean> setLoadingStream;

    @Inject
    MainPresenter presenter;

    public MainViewState() {
        setLoadingStream = new MutableLiveData<>();
    }

    /**
     * Подписывает вью на события потоков
     *
     * @param lifecycleOwner    владалец жизненного цикла, например фрагмент
     * @param view              вью, реализующая интерфейс MainContract.View
     */
    public void attachView(LifecycleOwner lifecycleOwner, MainContract.View view) {
        setLoadingStream.observe(lifecycleOwner, view::setLoading);
    }

    /**
     * Отписывает вью от событий потоков
     *
     * @param lifecycleOwner    владелец жизненного цикла
     */
    public void detachView(LifecycleOwner lifecycleOwner) {
        setLoadingStream.removeObservers(lifecycleOwner);
    }

    /**
     * Проводит внедрение зависимосией
     *
     * @param component даггер компонент
     */
    public void inject(@NonNull MainComponent component) {
        if (presenter == null) {
            component.inject(this);
            presenter.attachView(this);
        }
    }



    @Override
    public void setLoading(@NonNull Boolean isLoading) {

    }

    @Override
    public void setRatesAdapter(@NonNull CurrenciesAdapter adapter) {

    }
}
