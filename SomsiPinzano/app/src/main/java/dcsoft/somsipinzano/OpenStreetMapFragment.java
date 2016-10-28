package dcsoft.somsipinzano;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

interface OpenStreetMapFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class OpenStreetMapFragment extends Fragment {
    private static final String TAG = "OpenStreetMapFragment ";
    private View openStreetMapFragmentView;
    private MapView osmMap;
    private IMapController mapController;

    public OpenStreetMapFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public OpenStreetMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        openStreetMapFragmentView = inflater.inflate(R.layout.fragment_open_street_map, container, false);

        osmMap = (MapView) openStreetMapFragmentView.findViewById(R.id.osmMap);
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        osmMap.setBuiltInZoomControls(true);
        osmMap.setMultiTouchControls(true);
        osmMap.setTilesScaledToDpi(true);

        IMapController mapController = osmMap.getController();

        if (savedInstanceState == null) {
            org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

            mapController.setZoom(15);
            GeoPoint startPoint = new GeoPoint(46.1822, 12.9452);
            mapController.setCenter(startPoint);
        } else {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != nul");

            mapController.setZoom(savedInstanceState.getInt("zoom"));
            GeoPoint startPoint = new GeoPoint(savedInstanceState.getDouble("lat"), savedInstanceState.getDouble("lon"));
            mapController.setCenter(startPoint);
        }

        return openStreetMapFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        outState.putInt("zoom", osmMap.getZoomLevel());
        outState.putDouble("lat", osmMap.getMapCenter().getLatitude());
        outState.putDouble("lon", osmMap.getMapCenter().getLongitude());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //Log.d("DEBUGAPP", TAG + "onHiddenChanged");

        if (eseguiAlOnHiddenChanged != null) {
            eseguiAlOnHiddenChanged.esegui(hidden);
        }
    }
}
