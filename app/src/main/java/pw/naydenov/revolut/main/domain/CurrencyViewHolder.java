package pw.naydenov.revolut.main.domain;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import io.reactivex.subjects.PublishSubject;
import pw.naydenov.revolut.R;
import pw.naydenov.revolut.main.MainActivity;
import pw.naydenov.revolut.main.model.Currency;
import pw.naydenov.revolut.main.mvp.MainFragment;
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
            rate = currency.getRate();
            input.setText(String.format(
                    Locale.getDefault(),
                    "%.4f",
                    rate * multiplier
            ));
//            input.setFocusable(false);
//            input.setClickable(false);
        } else {

            input.setText(String.valueOf(multiplier));
//            input.setClickable(true);
//            input.setFocusable(true);
            input.requestFocus();

            InputMethodManager imm = (InputMethodManager) itemView.getContext().getSystemService(itemView.getContext().INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);
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
        this.multiplier = multiplier;
        input.setText(String.format(
                Locale.getDefault(),
                "%.4f",
                rate * this.multiplier
                )
        );
    }

    @Override
    public void updateRate(float rate) {
        this.rate = rate;
        input.setText(String.format(
                Locale.getDefault(),
                "%.4f",
                this.rate * multiplier
                )
        );
    }

    public void getFocus() {
        input.setFocusable(true);
        input.setClickable(true);
    }

    public void setInputListener(@NonNull TextWatcher inputListener){
        input.addTextChangedListener(inputListener);
    }

    public void removeInputListener(@NonNull TextWatcher inputListener){
        input.removeTextChangedListener(inputListener);
    }
}
