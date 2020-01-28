package pw.naydenov.revolut.main.model;

public class Url {

    /**
     * Апи для получения описания валют
     */
    public static final String CURRENCIES_DESCRIPTION_API = "https://free.currconv.com/api/v7/currencies?apiKey=do-not-use-this-key";

    /**
     * Апи для получения котировок
     */
    public static final String CURRENCIES_RATES_API = "https://revolut.duckdns.org/latest";

    /**
     * Имя параметра для указания базовой валюты для получения котировок с апи
     */
    public static final String CURRENCIES_BASE = "base";

}
