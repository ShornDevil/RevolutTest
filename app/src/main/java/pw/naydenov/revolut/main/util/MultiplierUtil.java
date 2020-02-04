package pw.naydenov.revolut.main.util;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Утилита для обмена множителем
 */
public class MultiplierUtil {
    private final float DEFAULT_MULTIPLIER = 1.0f;
    private float multiplier;
    private List<MultiplierListener> multiplierListeners;

    public MultiplierUtil() {
        multiplierListeners = new LinkedList<>();
        multiplier = DEFAULT_MULTIPLIER;
    }

    /**
     * @param listener Добавить слушателя изменений множителя и сразу сообщить текущий множитель
     */
    public void addMultiplierListener(@NonNull MultiplierListener listener) {
        multiplierListeners.add(listener);
        listener.onMultiplierChange(multiplier);
    }

    /**
     * @param listener Удалить слушателя из списка
     */
    public void removeMultiplierListener(@NonNull MultiplierListener listener) {
        multiplierListeners.remove(listener);
    }

    /**
     * Очистить список слушателей изменения множителя
     */
    public void clearMultiplierListeners() {
        multiplierListeners.clear();
    }

    /**
     * Обработать изменения множителя - уведомить слушателей и сохранить множитель
     * @param multiplier новый множитель
     */
    public void onMultiplierChange(float multiplier) {
        this.multiplier = multiplier;
        for (MultiplierListener listener : multiplierListeners) {
            listener.onMultiplierChange(multiplier);
        }
    }

    /**
     * Установить множитель по умолчанию (единица)
     */
    public void resetMultiplier() {
        multiplier = DEFAULT_MULTIPLIER;
    }

    /**
     * @return Возвращает текущий множитель
     */
    public float getMultiplier() {
        return multiplier;
    }
}
