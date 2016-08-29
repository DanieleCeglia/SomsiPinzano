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
    private CategoriaFragment categoriaFragment;
    private GoogleMapsFragment googleMapsFragment;
    private OpenStreetMapFragment openStreetMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUGAPP", TAG + "onCreate");

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragmentManager = getFragmentManager();
            categoriaFragment = new CategoriaFragment();
            googleMapsFragment = new GoogleMapsFragment();
            openStreetMapFragment = new OpenStreetMapFragment();

            openStreetMapFragment.eseguiAlOnHiddenChanged = new OpenStreetMapFragmentEseguiAlOnHiddenChanged() {
                @Override
                public void esegui(boolean hidden, TextView tvOSM) {
                    Log.d("DEBUGAPP", TAG + "OpenStreetMapFragmentEseguiAlOnStart");

                    if (!hidden) {
                        tvOSM.append(" asd");
                    }
                }
            };

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
    }

    private void eseguiAzione(int tabId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (tabId) {
            case R.id.item_pdi: {
                if (googleMapsFragment.isAdded()) {
                    fragmentTransaction.hide(googleMapsFragment);
                }
                if (openStreetMapFragment.isAdded()) {
                    fragmentTransaction.hide(openStreetMapFragment);
                }

                if (!categoriaFragment.isAdded()) {
                    fragmentTransaction.add(R.id.contentContainer, categoriaFragment);
                }

                fragmentTransaction.show(categoriaFragment);
            }
            break;
            case R.id.item_google_maps: {
                if (categoriaFragment.isAdded()) {
                    fragmentTransaction.hide(categoriaFragment);
                }
                if (openStreetMapFragment.isAdded()) {
                    fragmentTransaction.hide(openStreetMapFragment);
                }

                if (!googleMapsFragment.isAdded()) {
                    fragmentTransaction.add(R.id.contentContainer, googleMapsFragment);
                }

                fragmentTransaction.show(googleMapsFragment);
            }
            break;
            case R.id.item_open_street_map: {
                if (categoriaFragment.isAdded()) {
                    fragmentTransaction.hide(categoriaFragment);
                }
                if (googleMapsFragment.isAdded()) {
                    fragmentTransaction.hide(googleMapsFragment);
                }

                if (!openStreetMapFragment.isAdded()) {
                    fragmentTransaction.add(R.id.contentContainer, openStreetMapFragment);
                }

                fragmentTransaction.show(openStreetMapFragment);
            }
        }

        fragmentTransaction.commit();
    }

    public void categoriaScelta(String categoria) {
        Log.d("DEBUGAPP", TAG + "categoriaScelta: " + categoria);

        PdiFragment pdiFragment = new PdiFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentContainer, pdiFragment);
        fragmentTransaction.addToBackStack(pdiFragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public void pdiScelto(String pdi) {
        Log.d("DEBUGAPP", TAG + "pdiScelto: " + pdi);
    }
}
