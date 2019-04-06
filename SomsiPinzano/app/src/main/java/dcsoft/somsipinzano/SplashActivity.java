package dcsoft.somsipinzano;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Boolean timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String versionName = "non disponibile";

        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Integer versionCode = 0;

        try {
            versionCode = packageManager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "v. " + versionName + " (" + versionCode + ")", Toast.LENGTH_LONG).show();

        if (BuildConfig.DEBUG) {
            //Fabric.with(this, new Crashlytics());
            Stetho.initializeWithDefaults(this);
            //Bugsee.launch(this.getApplication(), "29ef72b9-804e-4fba-af76-ed830e32145e");
        }

        timeout = false;

        caricaFirebase();

        //caricaMainActivity();
    }

    void caricaFirebase() {
        final FirebaseHelper firebaseHelperCondiviso = FirebaseHelper.dammiFirebaseHelperCondiviso();
        firebaseHelperCondiviso.iniziaScaricareDb();

        firebaseHelperCondiviso.eseguiAlScaricamentoCompletato = new FirebaseHelperEseguiAlScaricamentoCompletato() {
            @Override
            public void esegui() {
                Log.d("DEBUGAPP", TAG + " [eseguiAlScaricamentoCompletato] scaricamentoDatabaseRiuscitoConSuccesso: " + firebaseHelperCondiviso.scaricamentoDatabaseRiuscitoConSuccesso());

                caricaMainActivity();
            }
        };

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                caricaMainActivity();
            }
        }, 10000); // attendo un timeout di 10 secondi prima caricare il db sqlite interno
    }

    void caricaMainActivity() {
        if (!timeout) {
            timeout = true;

            Log.d("DEBUGAPP", TAG + " [caricaMainActivity]");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}