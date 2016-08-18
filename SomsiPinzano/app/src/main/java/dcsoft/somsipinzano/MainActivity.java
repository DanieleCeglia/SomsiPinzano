package dcsoft.somsipinzano;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity ";
    private CoordinatorLayout coordinatorLayout;

    public PdiFragment pdiFragment;
    public GoogleMapsFragment googleMapsFragment;
    public OpenStreetMapFragment openStreetMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("DEBUGAPP", TAG + "onCreate");

        googleMapsFragment.getView().setVisibility(View.GONE);
        openStreetMapFragment.getView().setVisibility(View.GONE);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_tre_tab);

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.menu_tre_tab, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.item_pdi: {
                        Snackbar.make(coordinatorLayout, R.string.punti_di_interesse, Snackbar.LENGTH_LONG).show();

                        pdiFragment.getView().setVisibility(View.VISIBLE);
                        googleMapsFragment.getView().setVisibility(View.GONE);
                        openStreetMapFragment.getView().setVisibility(View.GONE);
                    }
                        break;
                    case R.id.item_google_maps: {
                        Snackbar.make(coordinatorLayout, "Google Maps", Snackbar.LENGTH_LONG).show();

                        pdiFragment.getView().setVisibility(View.GONE);
                        googleMapsFragment.getView().setVisibility(View.VISIBLE);
                        openStreetMapFragment.getView().setVisibility(View.GONE);
                    }
                        break;
                    case R.id.item_open_street_map: {
                        Snackbar.make(coordinatorLayout, "OpenStreetMap", Snackbar.LENGTH_LONG).show();

                        pdiFragment.getView().setVisibility(View.GONE);
                        googleMapsFragment.getView().setVisibility(View.GONE);
                        openStreetMapFragment.getView().setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#C2185B");
    }
}
