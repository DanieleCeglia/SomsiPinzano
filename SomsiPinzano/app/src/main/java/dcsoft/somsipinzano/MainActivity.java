package dcsoft.somsipinzano;

import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private CoordinatorLayout coordinatorLayout;
    private BottomBar mBottomBar;
    private View cView;
    private View gmView;
    private View opmView;

    public CategoriaFragment categoriaFragment;
    public GoogleMapsFragment googleMapsFragment;
    public OpenStreetMapFragment openStreetMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUGAPP", TAG + "onCreate");

        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_tre_tab);

        cView = categoriaFragment.getView();
        gmView = googleMapsFragment.getView();
        opmView = openStreetMapFragment.getView();

        assert cView != null;
        assert gmView != null;
        assert opmView != null;

        gmView.setVisibility(View.GONE);
        opmView.setVisibility(View.GONE);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.menu_tre_tab);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                eseguiAzione(menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                eseguiAzione(menuItemId);

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    private void eseguiAzione(int menuItemId) {
        switch (menuItemId) {
            case R.id.item_pdi: {
                Snackbar.make(coordinatorLayout, R.string.punti_di_interesse, Snackbar.LENGTH_LONG).show();

                cView.setVisibility(View.VISIBLE);
                gmView.setVisibility(View.GONE);
                opmView.setVisibility(View.GONE);
            }
            break;
            case R.id.item_google_maps: {
                Snackbar.make(coordinatorLayout, "Google Maps", Snackbar.LENGTH_LONG).show();

                cView.setVisibility(View.GONE);
                gmView.setVisibility(View.VISIBLE);
                opmView.setVisibility(View.GONE);
            }
            break;
            case R.id.item_open_street_map: {
                Snackbar.make(coordinatorLayout, "OpenStreetMap", Snackbar.LENGTH_LONG).show();

                cView.setVisibility(View.GONE);
                gmView.setVisibility(View.GONE);
                opmView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void categoriaScelta(String categoria) {
        Log.d("DEBUGAPP", TAG + "categoriaScelta: " + categoria);
    }
}
