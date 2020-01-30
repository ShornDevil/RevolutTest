package pw.naydenov.revolut.main.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import pw.naydenov.revolut.R;
import pw.naydenov.revolut.RevolutApplication;
import pw.naydenov.revolut.main.domain.CurrenciesAdapter;

/**
 * Главный экран с котировками
 */
public class MainFragment extends Fragment implements MainContract.View {
    public static final String TAG = "main_fragment";
    private MainViewState viewState;
    private RecyclerView currencies;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewState = ViewModelProviders.of(this).get(MainViewState.class);
        RevolutApplication provider = (RevolutApplication) Objects.requireNonNull(getActivity()).getApplication();
        viewState.inject(provider.getMainComponent());
        viewState.attachView(this, this);
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currencies = view.findViewById(R.id.main_fragment_currencies_recycler);
        currencies.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        viewState.viewCreated();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewState.detachView(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLoading(@NonNull Boolean isLoading) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRatesAdapter(@NonNull CurrenciesAdapter adapter) {
        currencies.setAdapter(adapter);
    }
}
