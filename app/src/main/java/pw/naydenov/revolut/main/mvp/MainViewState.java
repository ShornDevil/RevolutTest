package pw.naydenov.revolut.main.mvp;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import pw.naydenov.revolut.main.di.MainComponent;
import pw.naydenov.revolut.main.domain.CurrenciesAdapter;

public class MainViewState extends ViewModel implements MainContract.View, MainContract.Presenter {

    private MutableLiveData<Boolean> setLoadingStream;
    private MutableLiveData<CurrenciesAdapter> currenciesRatesAdapterStream;

    @Inject
    MainPresenter presenter;

    public MainViewState() {
        setLoadingStream = new MutableLiveData<>();
        currenciesRatesAdapterStream = new MutableLiveData<>();
    }

    /**
     * Подписывает вью на события потоков
     *
     * @param lifecycleOwner    владалец жизненного цикла, например фрагмент
     * @param view              вью, реализующая интерфейс MainContract.View
     */
    public void attachView(LifecycleOwner lifecycleOwner, MainContract.View view) {
        setLoadingStream.observe(lifecycleOwner, view::setLoading);
        currenciesRatesAdapterStream.observe(lifecycleOwner, view::setRatesAdapter);
    }

    /**
     * Отписывает вью от событий потоков
     *
     * @param lifecycleOwner    владелец жизненного цикла
     */
    public void detachView(LifecycleOwner lifecycleOwner) {
        setLoadingStream.removeObservers(lifecycleOwner);
        currenciesRatesAdapterStream.removeObservers(lifecycleOwner);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLoading(@NonNull Boolean isLoading) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRatesAdapter(@NonNull CurrenciesAdapter adapter) {
        currenciesRatesAdapterStream.postValue(adapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void viewCreated(@NonNull RecyclerView.LayoutManager layoutManager) {
        presenter.viewCreated(layoutManager);
    }
}
