package dcsoft.somsipinzano;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

interface GoogleMapsFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "GoogleMapsFragment ";
    private MainActivity mainActivity;
    private View googleMapsFragmentView;
    private MapView mMapView;
    private GoogleMap gmMap;

    public GoogleMapsFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        googleMapsFragmentView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        if (savedInstanceState != null) {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != nul");
        }

        mMapView = (MapView) googleMapsFragmentView.findViewById(R.id.gmMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // serve per far vedere la mappa immediatamente
        mMapView.getMapAsync(this);

        return googleMapsFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //Log.d("DEBUGAPP", TAG + "onHiddenChanged");

        if (eseguiAlOnHiddenChanged != null) {
            eseguiAlOnHiddenChanged.esegui(hidden);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmMap = googleMap;

        LatLng pinzano = new LatLng(46.1822, 12.9452);
        gmMap.addMarker(new MarkerOptions().position(pinzano).title("Pinzano al Tagliamento"));
        gmMap.moveCamera(CameraUpdateFactory.newLatLng(pinzano));
    }
}
