package pw.naydenov.revolut.main.domain;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import pw.naydenov.revolut.main.model.Currency;

/**
 * Адаптер для списка валют
 */
public class CurrenciesAdapter extends RecyclerView.Adapter {

    private List<Currency> currencies;
//    private MultiplierUpdater updater;
    private PublishSubject<Currency> currencyClickStream;

    public CurrenciesAdapter(@NonNull List<Currency> currencies) {
        this.currencies = currencies;
        currencyClickStream = PublishSubject.create();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    /**
     * @return  Возвращает поток кликов по валютам для дальнейшей обработки
     */
    public PublishSubject<Currency> getCurrencyClickStream() {
        return currencyClickStream;
    }

    public void updateRate(int position) {
        this.view
    }
}
