package dcsoft.somsipinzano;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bugsee.library.Bugsee;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);
        Bugsee.launch(this.getApplication(), "29ef72b9-804e-4fba-af76-ed830e32145e");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}