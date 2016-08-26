package dcsoft.somsipinzano;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private FragmentManager fragmentManager;

    public CategoriaFragment categoriaFragment;
    public GoogleMapsFragment googleMapsFragment;
    public OpenStreetMapFragment openStreetMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUGAPP", TAG + "onCreate");

        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    eseguiAzione(tabId);
                }
            });

            bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
                @Override
                public void onTabReSelected(@IdRes int tabId) {
                    eseguiAzione(tabId);
                }
            });
        }
    }

    private void eseguiAzione(int tabId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (tabId) {
            case R.id.item_pdi: {
                if (categoriaFragment == null) {
                    categoriaFragment = new CategoriaFragment();
                }
                fragmentTransaction.replace(R.id.contentContainer, categoriaFragment);
            }
            break;
            case R.id.item_google_maps: {
                if (googleMapsFragment == null) {
                    googleMapsFragment = new GoogleMapsFragment();
                }
                fragmentTransaction.replace(R.id.contentContainer, googleMapsFragment);
            }
            break;
            case R.id.item_open_street_map: {
                if (openStreetMapFragment == null) {
                    openStreetMapFragment = new OpenStreetMapFragment();
                }
                fragmentTransaction.replace(R.id.contentContainer, openStreetMapFragment);

                openStreetMapFragment.eseguiAlOnCreateView = new OpenStreetMapFragmentEseguiAlOnCreateView() {
                    @Override
                    public void esegui(TextView tvOSM) {
                        Log.d("DEBUGAPP", TAG + "OpenStreetMapFragmentEseguiAlOnCreateView");
                        tvOSM.append(" asd");
                    }
                };
            }
        }

        fragmentTransaction.commit();
    }

    public void categoriaScelta(String categoria) {
        Log.d("DEBUGAPP", TAG + "categoriaScelta: " + categoria);
    }
}
