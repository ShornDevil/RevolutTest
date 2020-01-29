package pw.naydenov.revolut.main;

import android.app.Application;

import pw.naydenov.revolut.main.di.DaggerMainComponent;
import pw.naydenov.revolut.main.di.MainComponent;

public class RevolutApplication extends Application {

    private static MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mainComponent = DaggerMainComponent.create();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
