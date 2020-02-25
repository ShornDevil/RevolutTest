package pw.naydenov.revolut.main.mvp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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

    private DisposableContainer disposableContainer;

    public MainPresenter(@NonNull MainReository reository,
                         @NonNull MultiplierUtil multiplier) {
        this.reository = reository;
        this.multiplier = multiplier;

        baseCurrency = "EUR";
        viewCurrencies = new LinkedList<>();
        adapter = new CurrenciesAdapter(viewCurrencies, multiplier);
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
                            adapter.removeMultiplierListener(viewCurrencies.indexOf(item));
                            viewCurrencies.clear();
                            multiplier.clearMultiplierListeners();
                        })
        );

        disposableContainer.add(
                adapter
                        .getMultiplierChangeStream()
                        .subscribe(multiplierString -> {
                            try {
                                float newMultiplier = Float.parseFloat(multiplierString);
                                multiplier.onMultiplierChange(newMultiplier);
                            } catch (NumberFormatException ex) {
                            }
                        })
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void viewCreated(@NonNull RecyclerView.LayoutManager layoutManager) {
        view.setRatesAdapter(adapter);

        disposableContainer.add(Observable.interval(1000, TimeUnit.MILLISECONDS)
                .map(hz -> reository.requestRates(Url.CURRENCIES_RATES_API, baseCurrency))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ratesObject -> {
                    updateRates(ratesObject);
                })
        );

        disposableContainer.add(
                Single
                        .fromCallable(() -> reository.requestSymbols(Url.CURRENCIES_DESCRIPTION_API))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(symbolsResponse -> symbols = symbolsResponse)
        );
    }

    /**
     * Обновляет котировки, сверяет базовую валюту, добавляет недостающие валюты
     *
     * @param ratesObject объект с котривками, полученый с бэка
     */
    private void updateRates(Object ratesObject) {
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

                if (viewCurrencies.get(0).getDescriprion() == null && symbols != null) {
                    if (symbols.getSuccess() != null && symbols.getSuccess()) {
                        viewCurrencies.get(0).setDescriprion(symbols.getSymbols().get(viewCurrencies.get(0).getId()));
                    }
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
                        adapter.updateRate(i, currency.getRate());

                    }
                    if (currency.getDescriprion() == null && symbols != null) {
                        if (symbols.getSuccess() != null && symbols.getSuccess()) {
                            currency.setDescriprion(symbols.getSymbols().get(currency.getId()));
                            adapter.notifyItemChanged(i);
                        }
                    }
                    i++;
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
                // something wrong - no rates received
            }
        }
    }
}
