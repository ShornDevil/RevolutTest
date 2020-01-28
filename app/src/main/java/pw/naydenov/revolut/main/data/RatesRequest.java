package pw.naydenov.revolut.main.data;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Request;
import pw.naydenov.revolut.main.model.Url;

/**
 * Запрос котировок валют
 */
public class RatesRequest {

    private String baseCurrency;
    private String apiUrl;

    public RatesRequest(@NonNull String apiUrl, @NonNull String baseCurrency) {
        this.apiUrl = apiUrl;
        this.baseCurrency = baseCurrency;
    }

    public Request create() throws MalformedURLException {
        URL url = new URL(apiUrl);
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(url.getProtocol())
                .host(url.getHost())
                .addQueryParameter(Url.CURRENCIES_BASE, baseCurrency);

        return new Request.Builder()
                .url(builder.build())
                .build();
    }
}
