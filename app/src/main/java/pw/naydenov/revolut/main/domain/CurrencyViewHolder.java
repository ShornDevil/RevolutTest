package pw.naydenov.revolut.main.domain;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pw.naydenov.revolut.R;
import pw.naydenov.revolut.main.model.Currency;
import pw.naydenov.revolut.main.util.MultiplierListener;
import pw.naydenov.revolut.main.util.RateUpdatable;

/**
 * Вьюхолдер элемента списка валют
 */
public class CurrencyViewHolder extends RecyclerView.ViewHolder implements MultiplierListener, RateUpdatable {

    private ImageView flag;
    private TextView name;
    private TextView description;
    private EditText input;
    private float rate;
    private float multiplier;

    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);
        flag = itemView.findViewById(R.id.currency_view_holder_country_flag);
        name = itemView.findViewById(R.id.currency_view_holder_name);
        description = itemView.findViewById(R.id.currency_view_holder_description);
        input = itemView.findViewById(R.id.currency_view_holder_input);
    }

    public void setData(@NonNull Currency currency, float multiplier) {
        this.multiplier = multiplier;
        name.setText(currency.getId());
        if (currency.getRate() > 0.0f) {
            input.setText((currency.getRate()*multiplier) + "");
            input.setFocusable(false);
            input.setClickable(false);
        } else {
            input.setText(String.valueOf(multiplier));
            input.setClickable(true);
            input.setFocusable(true);
            input.requestFocus();
        }
        if (currency.getDescriprion() != null) {
            description.setText(currency.getDescriprion());
        }
        int drawableResourceId = itemView.getResources().getIdentifier(currency.getId().toLowerCase(), "drawable", itemView.getContext().getPackageName());
        if (drawableResourceId > 0) {
            flag.setImageDrawable(itemView.getResources().getDrawable(drawableResourceId));
        } else {
            flag.setImageDrawable(itemView.getResources().getDrawable(R.drawable.revolut_logo));
        }
    }

    @Override
    public void onMultiplierChange(float multiplier) {

    }

    @Override
    public void updateRate(float rate) {
        input.setText(rate*multiplier + "");
    }
}
