package pw.naydenov.revolut.main.domain;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import pw.naydenov.revolut.R;
import pw.naydenov.revolut.main.model.Currency;
import pw.naydenov.revolut.main.util.MultiplierUtil;

/**
 * Адаптер для списка валют
 */
public class CurrenciesAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private List<Currency> currencies;
    private MultiplierUtil multiplierUtil;
    private PublishSubject<Currency> currencyClickStream;
    private PublishSubject<String> multiplierChangeStream;
    private RecyclerView recyclerView;
    private TextWatcher inputTextWatcher;

    public CurrenciesAdapter(@NonNull List<Currency> currencies, @NonNull MultiplierUtil multiplierUtil) {
        this.currencies = currencies;
        this.multiplierUtil = multiplierUtil;
        currencyClickStream = PublishSubject.create();
        multiplierChangeStream = PublishSubject.create();
        inputTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                multiplierChangeStream.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.setData(currencies.get(position), multiplierUtil.getMultiplier());
        holder.itemView.setOnClickListener(view -> currencyClickStream.onNext(currencies.get(position)));
        if (position == 0) {
            holder.setInputListener(inputTextWatcher);
        } else {
            multiplierUtil.addMultiplierListener(holder);
        }
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    /**
     * Обновить цену валюты на определённой позиции без полной перерисовки вьюхолдера, если её видно на экране
     *
     * @param position  позиция, на которой находится нужная валюта
     * @param rate      цена валюты относительно базовой
     */
    public void updateRate(int position, float rate) {
        if (recyclerView != null) {
            CurrencyViewHolder holder = (CurrencyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            if (holder != null) {
                holder.updateRate(rate);
            }
        }
    }

    public PublishSubject<String> getMultiplierChangeStream() {
        return multiplierChangeStream;
    }

    public void removeMultiplierListener(int position) {
        if (recyclerView != null) {
            CurrencyViewHolder holder = (CurrencyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            if (holder != null) {
                holder.removeInputListener(inputTextWatcher);
            }
        }
    }
}
