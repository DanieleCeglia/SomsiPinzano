package dcsoft.somsipinzano;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

interface GoogleMapsFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "GoogleMapsFragment ";
    private MainActivity mainActivity;
    private View googleMapsFragmentView;
    private MapView mMapView;
    private GoogleMap gmMap;
    private float zoom = -1;
    private double lat = -1;
    private double lon = -1;
    private ArrayList<Pdi> listaPdi;

    public GoogleMapsFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }

    //region Metodi override
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        googleMapsFragmentView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        mMapView = (MapView) googleMapsFragmentView.findViewById(R.id.gmMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // serve per far vedere la mappa immediatamente
        mMapView.getMapAsync(this);

        if (savedInstanceState != null) {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != null");

            zoom = savedInstanceState.getFloat("zoom");
            lat = savedInstanceState.getDouble("lat");
            lon = savedInstanceState.getDouble("lon");
        }

        return googleMapsFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        if (gmMap != null) {
            CameraPosition cameraPosition = gmMap.getCameraPosition();

            if (cameraPosition != null) {
                outState.putFloat("zoom", gmMap.getCameraPosition().zoom);
                outState.putDouble("lat", gmMap.getCameraPosition().target.latitude);
                outState.putDouble("lon", gmMap.getCameraPosition().target.longitude);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //Log.d("DEBUGAPP", TAG + "onHiddenChanged");

        zoommaSuPdiSceltoSeNecessario();

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
        //Log.d("DEBUGAPP", TAG + "onMapReady");

        gmMap = googleMap;

        mainActivity.databaseAdapter.apriConnesioneDatabase();
        listaPdi = mainActivity.databaseAdapter.dammiPdi();

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gmMap.setMyLocationEnabled(true);
        }

        LatLng pinzano = new LatLng(46.1822, 12.9452);

        if (!zoommaSuPdiSceltoSeNecessario()) {
            if (lat == -1) {
                gmMap.moveCamera(CameraUpdateFactory.newLatLng(pinzano));
                gmMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            } else {
                LatLng centroMappaSalvato = new LatLng(lat, lon);

                gmMap.moveCamera(CameraUpdateFactory.newLatLng(centroMappaSalvato));
                gmMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
            }
        }

        for (int i = 0; i < listaPdi.size(); i++) {
            Pdi pdi = listaPdi.get(i);

            LatLng posizione = new LatLng(pdi.getLatitudine(), pdi.getLongitudine());
            MarkerOptions markerOptions = new MarkerOptions().position(posizione);

            Categoria categoria = mainActivity.databaseAdapter.dammiCategoria(pdi.getIdPdi_idCategoria());
            if (categoria != null) {
                markerOptions.icon(getMarkerIcon("#" + categoria.getColoreEsadecimale()));
            }

            switch (mainActivity.databaseAdapter.getLingua()) {
                case "italiano": {
                    markerOptions.title(pdi.getTitoloItaliano());
                }
                break;

                default: {
                    markerOptions.title(pdi.getTitoloInglese());
                }
            }

            gmMap.addMarker(markerOptions);
        }

        mainActivity.databaseAdapter.chiudiConnessioneDatabase();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
    //endregion

    //region Metodi privati
    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];

        Color.colorToHSV(Color.parseColor(color), hsv);

        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    private boolean zoommaSuPdiSceltoSeNecessario() {
        if (mainActivity.vediPdiSceltoSuGM) {
            mainActivity.vediPdiSceltoSuGM = false;

            LatLng pdiDaZoommare = new LatLng(mainActivity.pdiScelto.getLatitudine(), mainActivity.pdiScelto.getLongitudine());

            gmMap.moveCamera(CameraUpdateFactory.newLatLng(pdiDaZoommare));
            gmMap.animateCamera(CameraUpdateFactory.zoomTo(18));

            return true;
        }

        return false;
    }
    //endregion
}
