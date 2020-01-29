package pw.naydenov.revolut.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Objects;

/**
 * Модель объекта поддерживаемых валют и их описаний, приходит с апи
 */
public class Symbols {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("symbols")
    @Expose
    private Map<String, String> symbols;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbols symbols1 = (Symbols) o;
        return Objects.equals(success, symbols1.success) &&
                Objects.equals(symbols, symbols1.symbols);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, symbols);
    }

    @Override
    public String toString() {
        return "Symbols{" +
                "success=" + success +
                ", symbols=" + symbols +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }
}
