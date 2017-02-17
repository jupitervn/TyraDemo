package vn.jupiter.tyrademo;

import android.app.Application;

import com.parse.Parse;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.Configuration parseConfig = new Parse.Configuration.Builder(this)
                .applicationId("12345")
                .clientKey("xyz")
                .server("http://tyraserver-alfa.jelastic.elastx.net/parse/")
                .build();
        Parse.initialize(parseConfig);
    }
}
