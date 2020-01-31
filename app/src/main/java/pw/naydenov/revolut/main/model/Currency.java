package pw.naydenov.revolut.main.model;

import java.util.Objects;

/**
 * Модель валюты для отображения на экране
 */
public class Currency {

    private String id;
    private String descriprion;
    private float rate = -1.1f;
    private String icon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Float.compare(currency.rate, rate) == 0 &&
                Objects.equals(id, currency.id) &&
                Objects.equals(descriprion, currency.descriprion) &&
                Objects.equals(icon, currency.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descriprion, rate, icon);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", descriprion='" + descriprion + '\'' +
                ", rate=" + rate +
                ", icon='" + icon + '\'' +
                '}';
    }

    public String getDescriprion() {
        return descriprion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String descriprion;
        private float rate;
        private String icon;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder descriprion(String descriprion) {
            this.descriprion = descriprion;
            return this;
        }

        public Builder rate(float rate) {
            this.rate = rate;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Currency build() {
            Currency currency = new Currency();
            currency.setId(id);
            currency.setDescriprion(descriprion);
            currency.setRate(rate);
            currency.setIcon(icon);
            return currency;
        }
    }
}
