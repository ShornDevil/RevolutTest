package pw.naydenov.revolut.main.mvp;

import androidx.annotation.NonNull;

import pw.naydenov.revolut.main.domain.CurrenciesAdapter;

/**
 * Интерфейс взаиможействия между презентером и фрагментом главного экрана
 */
public interface MainContract {
    interface View {

        /**
         * Задать видимость индикатора загрузки
         *
         * @param isLoading видимость индикатора загрузки (true - видно, false - не видно)
         */
        void setLoading(@NonNull Boolean isLoading);

        /**
         * Задать адаптер для отображения списка валют
         *
         * @param adapter адаптер для списка валют
         */
        void setRatesAdapter(@NonNull CurrenciesAdapter adapter);

    }

    interface Presenter {


    }
}
