package pw.naydenov.revolut.main.domain;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import pw.naydenov.revolut.R;
import pw.naydenov.revolut.main.model.Currency;

/**
 * Адаптер для списка валют
 */
public class CurrenciesAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private List<Currency> currencies;
//    private MultiplierUpdater updater;
    private PublishSubject<Currency> currencyClickStream;

    public CurrenciesAdapter(@NonNull List<Currency> currencies) {
        this.currencies = currencies;
        currencyClickStream = PublishSubject.create();
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.setData(currencies.get(position));
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

}
