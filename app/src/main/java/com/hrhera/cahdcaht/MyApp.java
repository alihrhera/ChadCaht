package com.hrhera.cahdcaht;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private Context context;
    private MyApp() {
    }

   private static MyApp app;

    public static synchronized MyApp getInstance() {
        if (app == null) {
            app = new MyApp();
        }

        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }


    public Context getContext() {
        return context;
    }
}
