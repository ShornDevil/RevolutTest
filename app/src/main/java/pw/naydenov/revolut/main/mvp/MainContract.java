package pw.naydenov.revolut.main.mvp;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        /**
         * Обновить котировку у итема на определённой позиции
         *
         * @param positionAndRate пара - позиция и новая цена
         */
        void updateItemAtPosition(@NonNull Pair<Integer, Float> positionAndRate);

    }

    interface Presenter {

        /**
         * Уведомить презентер о создании вью
         */
        void viewCreated(RecyclerView.LayoutManager layoutManager);


    }
}
