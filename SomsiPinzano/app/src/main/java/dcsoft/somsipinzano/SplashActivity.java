package dcsoft.somsipinzano;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.facebook.stetho.Stetho;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Boolean timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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