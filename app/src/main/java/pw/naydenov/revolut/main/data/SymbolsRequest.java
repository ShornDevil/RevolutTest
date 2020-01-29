package pw.naydenov.revolut.main.data;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Request;
import pw.naydenov.revolut.main.model.Url;

public class SymbolsRequest {

    private String apiUrl;

    public SymbolsRequest(@NonNull String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Request create() throws MalformedURLException {
        URL url = new URL(apiUrl);
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(url.getProtocol())
                .host(url.getHost())
                .addPathSegment(Url.SUPPORTED_SYMBOLS_ENDPOINT)
                .addQueryParameter(Url.ACCESS_KEY_PARAMETER, Url.FIXER_API_KEY);

        return new Request.Builder()
                .url(builder.build())
                .build();
    }
}

