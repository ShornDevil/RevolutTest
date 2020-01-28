package pw.naydenov.revolut.main.domain;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pw.naydenov.revolut.R;
import pw.naydenov.revolut.main.model.Currency;
import pw.naydenov.revolut.main.util.MultiplierListener;

/**
 * Вьюхолдер элемента списка валют
 */
public class CurrencyViewHolder extends RecyclerView.ViewHolder implements MultiplierListener {

    private ImageView flag;
    private TextView name;
    private TextView description;
    private EditText input;
    private Currency currency;
    private float multiplier;

    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);
        flag = itemView.findViewById(R.id.currency_view_holder_country_flag);
        name = itemView.findViewById(R.id.currency_view_holder_name);
        description = itemView.findViewById(R.id.currency_view_holder_description);
        input = itemView.findViewById(R.id.currency_view_holder_input);
    }

    public void setData(@NonNull Currency currency) {
        this.currency = currency;
    }

    @Override
    public void onMultiplierChange(float multiplier) {

    }
}
