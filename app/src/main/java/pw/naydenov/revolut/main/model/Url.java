package pw.naydenov.revolut.main.model;

public class Url {

    /**
     * Апи для получения описания валют
     */
    public static final String CURRENCIES_DESCRIPTION_API = "http://data.fixer.io/api/";

    /**
     * Апи для получения котировок
     */
    public static final String CURRENCIES_RATES_API = "https://revolut.duckdns.org/";

    /**
     * Фрагмент пути до котировок
     */
    public static final String CURRENCIES_LATEST_ENDPOINT = "latest";

    /**
     * Фрагмент пути до валют и описаний
     */
    public static final String SUPPORTED_SYMBOLS_ENDPOINT = "symbols";

    /**
     * Имя параметра для указания базовой валюты для получения котировок с апи
     */
    public static final String CURRENCIES_BASE = "base";

    /**
     * Ключ для доступа к апи fixer.io
     */
    public static final String FIXER_API_KEY = "6148765ef0b3cd3623c7bcfa11468bf1";

    /**
     * Имя параметра для передачи ключа
     */
    public static final String ACCESS_KEY_PARAMETER = "access_key";

}
