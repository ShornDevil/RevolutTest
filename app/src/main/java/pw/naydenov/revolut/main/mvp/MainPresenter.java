package pw.naydenov.revolut.main.mvp;

import android.util.Log;

import java.util.Map;

import pw.naydenov.revolut.main.model.Currency;

/**
 * Презентер главного экрана
 */
public class MainPresenter implements MainContract.Presenter {

    private Map<String, Currency> currencyMap;
    private MainContract.View view;

    public MainPresenter() {

    }


    /**
     * Соединяет вью и презентер
     */
    public void attachView(MainContract.View view) {
        this.view = view;
        Log.e("TAG", "attachView: CALLED! View ATTACHED!");
    }

}
