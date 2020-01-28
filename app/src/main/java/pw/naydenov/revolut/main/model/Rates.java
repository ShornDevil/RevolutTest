package pw.naydenov.revolut.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Objects;

/**
 * Модель объекта котировок, приходит с апи революта
 */
public class Rates {

    @SerializedName("base")
    @Expose
    private String base;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("rates")
    @Expose
    private Map<String, Float> rates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rates rates1 = (Rates) o;
        return Objects.equals(base, rates1.base) &&
                Objects.equals(date, rates1.date) &&
                Objects.equals(rates, rates1.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, date, rates);
    }

    @Override
    public String toString() {
        return "Rates{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }
}
