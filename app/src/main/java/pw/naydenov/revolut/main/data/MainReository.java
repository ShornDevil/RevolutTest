package pw.naydenov.revolut.main.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import pw.naydenov.revolut.main.model.Rates;

/**
 * Репозиторий для главного экрана
 */
public class MainReository {

    private OkHttpClient okHttpClient;
    private Gson gson;

    public MainReository(@NonNull OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        gson = new Gson();
    }

    /**
     * Запрашивает котировки с апи
     *
     * @param url           URL адрес апи
     * @param currencyId    базовая валюта (относительно которой нужны котировки)
     * @return              объект котировок
     * @throws MalformedURLException
     * @throws IOException
     */
    public Rates requestRates(@NonNull String url, @NonNull String currencyId) throws MalformedURLException, IOException {
        Response response = okHttpClient.newCall(new RatesRequest(url, currencyId).create()).execute();
        return gson.fromJson(response.body().string(), Rates.class);
    }

    /**
     * Вытаскивает из ответа хэдэры и помещает в мапу
     *
     * @param response ответ сервера "raw"
     * @return словарь хэдэров
     */
    public Map<String, String> extractHeaders(@NonNull Response response) {
        Headers headers = response.headers();
        Map<String, String> headersMap = new HashMap<>();
        for (String name : headers.names()) {
            String value = headers.get(name);
            if (value != null) {
                headersMap.put(name, value);
            }
        }
        return headersMap;
    }
}