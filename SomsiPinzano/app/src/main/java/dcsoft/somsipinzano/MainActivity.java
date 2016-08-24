package dcsoft.somsipinzano;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";

    //public CategoriaFragment categoriaFragment = new CategoriaFragment();
    //public GoogleMapsFragment googleMapsFragment = new GoogleMapsFragment();
    //public OpenStreetMapFragment openStreetMapFragment = new OpenStreetMapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUGAPP", TAG + "onCreate");

        setContentView(R.layout.activity_main);

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

        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_categoria, categoriaFragment);
        //fragmentTransaction.commit();
    }

    private void eseguiAzione(int menuItemId) {
        switch (menuItemId) {
            case R.id.item_pdi: {
            }
            break;
            case R.id.item_google_maps: {
            }
            break;
            case R.id.item_open_street_map: {
            }
        }
    }

    public void categoriaScelta(String categoria) {
        Log.d("DEBUGAPP", TAG + "categoriaScelta: " + categoria);
    }
}
