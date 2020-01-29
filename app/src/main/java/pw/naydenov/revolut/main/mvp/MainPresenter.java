package pw.naydenov.revolut.main.mvp;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.schedulers.Schedulers;
import pw.naydenov.revolut.main.data.MainReository;
import pw.naydenov.revolut.main.domain.CurrenciesAdapter;
import pw.naydenov.revolut.main.model.Currency;
import pw.naydenov.revolut.main.model.Rates;
import pw.naydenov.revolut.main.model.Url;

/**
 * Презентер главного экрана
 */
public class MainPresenter implements MainContract.Presenter {

    private Map<String, Currency> currencyMap;
    private List<Currency> viewCurrencies;
    private MainContract.View view;
    private MainReository reository;
    private CurrenciesAdapter adapter;
    private boolean isStop = false;

    private DisposableContainer disposableContainer;

    public MainPresenter(@NonNull MainReository reository) {
        this.reository = reository;

        viewCurrencies = new ArrayList<>();
        adapter = new CurrenciesAdapter(viewCurrencies);
        disposableContainer = new CompositeDisposable();
    }

    /**
     * Соединяет вью и презентер
     */
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void viewCreated() {
//        view.setRatesAdapter(adapter);


        disposableContainer.add(Observable.create(
                subscriber -> {
                    while (true) {
                        subscriber.onNext(reository.requestRates(Url.CURRENCIES_RATES_API, "EUR"));
                        Thread.sleep(1000);
                        if (isStop) {
                            break;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rates -> Log.e("TAG", "viewCreated: " + rates))
        );
/*
        disposableContainer.add(Observable
                .fromCallable(() -> {
                    return reository.requestRates(Url.CURRENCIES_RATES_API, "EUR");
                })
                .map(rates -> {

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
*/
    }
}
