package pw.naydenov.revolut.main.mvp;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.schedulers.Schedulers;
import pw.naydenov.revolut.main.data.MainReository;
import pw.naydenov.revolut.main.domain.CurrenciesAdapter;
import pw.naydenov.revolut.main.model.Currency;
import pw.naydenov.revolut.main.model.Rates;
import pw.naydenov.revolut.main.model.Symbols;
import pw.naydenov.revolut.main.model.Url;
import pw.naydenov.revolut.main.util.MultiplierUtil;

/**
 * Презентер главного экрана
 */
public class MainPresenter implements MainContract.Presenter {

    private MainReository reository;
    private MultiplierUtil multiplier;

    private String baseCurrency;
    private Symbols symbols;
    private List<Currency> viewCurrencies;
    private MainContract.View view;
    private CurrenciesAdapter adapter;
    private boolean isStop = false;

    private DisposableContainer disposableContainer;

    public MainPresenter(@NonNull MainReository reository,
                         @NonNull MultiplierUtil multiplier) {
        this.reository = reository;
        this.multiplier = multiplier;

        baseCurrency = "EUR";
        viewCurrencies = new LinkedList<>();
        adapter = new CurrenciesAdapter(viewCurrencies);
        disposableContainer = new CompositeDisposable();
    }

    /**
     * Соединяет вью и презентер
     */
    public void attachView(MainContract.View view) {
        this.view = view;
        disposableContainer.add(
                adapter
                        .getCurrencyClickStream()
                        .subscribe(item -> {
                            baseCurrency = item.getId();
                            int oldPosition = viewCurrencies.indexOf(item);
                            viewCurrencies.remove(item);
                            item.setRate(-1.0f);
                            viewCurrencies.add(0, item);
                            adapter.notifyItemMoved(oldPosition, 0);
                        })
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void viewCreated() {
        view.setRatesAdapter(adapter);

        disposableContainer.add(Observable.create(
                subscriber -> {
                    while (true) {
                        subscriber.onNext(reository.requestRates(Url.CURRENCIES_RATES_API, baseCurrency));
                        Thread.sleep(1000);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ratesObject -> {
                    updateRates(ratesObject);
                })
        );

        disposableContainer.add(
                Single
                        .fromCallable(() -> reository.requestSymbols())
        );
    }

    private void updateRates (Object ratesObject) {
        if (ratesObject instanceof Rates) {
            Rates rates = (Rates) ratesObject;
            if (rates.getBase() != null && rates.getDate() != null && rates.getRates() != null) {
                boolean refreshFlag = false;
                if (viewCurrencies.isEmpty()) {
                    viewCurrencies.add(Currency
                            .builder()
                            .id(rates.getBase())
                            .build()
                    );
                    refreshFlag = true;
                } else if (!viewCurrencies.get(0).getId().equals(rates.getBase())) {
                    viewCurrencies.clear();
                    viewCurrencies.add(Currency
                            .builder()
                            .id(rates.getBase())
                            .build()
                    );
                    refreshFlag = true;
                }

                Iterator<Currency> iterator = viewCurrencies.iterator();
                if (iterator.hasNext()) {
                    iterator.next();
                }
                int i = 1;
                while (iterator.hasNext()) {
                    Currency currency = iterator.next();
                    if (rates.getRates().containsKey(currency.getId())) {
                        currency.setRate(rates.getRates().get(currency.getId()));
                        rates.getRates().remove(currency.getId());
                        adapter.notifyItemChanged(i);
                        i++;
                    }
                    if (currency.getDescriprion() == null || currency.getDescriprion().isEmpty() && symbols.getSuccess()) {
                        currency.setDescriprion(symbols.getSymbols().get(currency.getId()));
                    }
                }

                if (!rates.getRates().isEmpty()) {
                    for (Map.Entry<String, Float> rate : rates.getRates().entrySet()) {
                        viewCurrencies.add(Currency
                                .builder()
                                .id(rate.getKey())
                                .rate(rate.getValue())
                                .build()
                        );
                    }
                    adapter.notifyDataSetChanged();

                }

                if (refreshFlag) {
                    multiplier.resetMultiplier();
                }

            } else {
                // something wrong
            }
        }
    }
}
