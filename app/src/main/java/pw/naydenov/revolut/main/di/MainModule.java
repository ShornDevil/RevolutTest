package pw.naydenov.revolut.main.di;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pw.naydenov.revolut.main.data.MainReository;
import pw.naydenov.revolut.main.mvp.MainPresenter;
import pw.naydenov.revolut.main.util.MultiplierUtil;

@Module
public class MainModule {

    @Provides
    @Singleton
    MultiplierUtil getMultiplierUtil() {
        return new MultiplierUtil();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    MainReository provideMainRepository(OkHttpClient okHttpClient) {
        return new MainReository(okHttpClient);
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }
}
