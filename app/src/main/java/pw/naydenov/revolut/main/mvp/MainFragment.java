package pw.naydenov.revolut.main.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import pw.naydenov.revolut.R;
import pw.naydenov.revolut.main.domain.CurrenciesAdapter;

/**
 * Главный экран с котировками
 */
public class MainFragment extends Fragment implements MainContract.View {
    public static final String TAG = "main_fragment";
    private MainViewState viewState;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewState = ViewModelProviders.of(this).get(MainViewState.class);
        viewState.attachView(this, this);
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setLoading(@NonNull Boolean isLoading) {

    }

    @Override
    public void setRatesAdapter(@NonNull CurrenciesAdapter adapter) {

    }
}
