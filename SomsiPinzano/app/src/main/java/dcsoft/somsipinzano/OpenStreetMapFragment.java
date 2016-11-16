package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

interface OpenStreetMapFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class OpenStreetMapFragment extends Fragment {
    private static final String TAG = "OpenStreetMapFragment ";
    private MainActivity mainActivity;
    private View openStreetMapFragmentView;
    private MapView osmMap;
    private CompassOverlay compassOverlay;
    private MyLocationNewOverlay locationOverlay;
    private IMapController mapController;
    private ArrayList<Pdi> listaPdi;

    public OpenStreetMapFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public OpenStreetMapFragment() {
        // Required empty public constructor
    }

    //region Metodi override
    @Override
    public void onAttach(Activity activity) { // per API < 23
        super.onAttach(activity);

        //Log.d("DEBUGAPP", TAG + "onAttach");

        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

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

        openStreetMapFragmentView = inflater.inflate(R.layout.fragment_open_street_map, container, false);

        osmMap = (MapView) openStreetMapFragmentView.findViewById(R.id.osmMap);
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        osmMap.setBuiltInZoomControls(true);
        osmMap.setMultiTouchControls(true);
        osmMap.setTilesScaledToDpi(true);

        compassOverlay = new CompassOverlay(mainActivity, new InternalCompassOrientationProvider(mainActivity), osmMap);
        compassOverlay.enableCompass();
        osmMap.getOverlays().add(compassOverlay);

        //GpsMyLocationProvider gpsLocationProvider = new GpsMyLocationProvider(mainActivity);
        //gpsLocationProvider.setLocationUpdateMinTime(1000);
        //gpsLocationProvider.setLocationUpdateMinDistance(1);

        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(mainActivity), osmMap);
        locationOverlay.enableMyLocation();
        osmMap.getOverlays().add(locationOverlay);

        mapController = osmMap.getController();

        if (savedInstanceState == null) {
            org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

            if (!zoommaSuPdiSceltoSeNecessario()) {
                mapController.setZoom(15);
                GeoPoint startPoint = new GeoPoint(46.1822, 12.9452);
                mapController.setCenter(startPoint);
            }
        } else {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != null");

            if (!zoommaSuPdiSceltoSeNecessario()) {
                mapController.setZoom(savedInstanceState.getInt("zoom"));
                GeoPoint startPoint = new GeoPoint(savedInstanceState.getDouble("lat"), savedInstanceState.getDouble("lon"));
                mapController.setCenter(startPoint);
            }
        }


        mainActivity.databaseAdapter.apriConnesioneDatabase();
        listaPdi = mainActivity.databaseAdapter.dammiPdi();

        for (int i = 0; i < listaPdi.size(); i++) {
            Pdi pdi = listaPdi.get(i);

            GeoPoint posizione = new GeoPoint(pdi.getLatitudine(), pdi.getLongitudine());

            Marker marker = new Marker(osmMap);
            marker.setPosition(posizione);
            //marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            Categoria categoria = mainActivity.databaseAdapter.dammiCategoria(pdi.getIdPdi_idCategoria());
            if (categoria != null) {
                //marker.setIcon(getResources().getDrawable(R.drawable.marker_kml_point).mutate());
                //marker.setImage(getResources().getDrawable(R.drawable.ic_launcher));
                //marker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, map));
            }

            switch (mainActivity.databaseAdapter.getLingua()) {
                case "italiano": {
                    marker.setTitle(pdi.getTitoloItaliano());
                }
                break;

                default: {
                    marker.setTitle(pdi.getTitoloInglese());
                }
            }

            marker.setDraggable(true);
            marker.setOnMarkerDragListener(new OnMarkerDragListenerDrawer());
            osmMap.getOverlays().add(marker);
        }

        mainActivity.databaseAdapter.chiudiConnessioneDatabase();

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

        zoommaSuPdiSceltoSeNecessario();

        if (eseguiAlOnHiddenChanged != null) {
            eseguiAlOnHiddenChanged.esegui(hidden);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
    //endregion

    //region Metodi privati
    private boolean zoommaSuPdiSceltoSeNecessario() {
        if (mainActivity.vediPdiSceltoSuOSM) {
            mainActivity.vediPdiSceltoSuOSM = false;

            mapController.setZoom(18);
            GeoPoint startPoint = new GeoPoint(mainActivity.pdiScelto.getLatitudine(), mainActivity.pdiScelto.getLongitudine());
            mapController.setCenter(startPoint);

            return true;
        }

        return false;
    }
    //endregion

    //region Metodi pubblici
    public void impostaMappaNormale () {
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
    }

    public void impostaMappaCiclabile () {
        osmMap.setTileSource(TileSourceFactory.CYCLEMAP);
    }

    public void impostaMappaTrasporti () {
        osmMap.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT);
    }
    //endregion


    class OnMarkerDragListenerDrawer implements Marker.OnMarkerDragListener {
        ArrayList<GeoPoint> mTrace;
        Polyline mPolyline;

        OnMarkerDragListenerDrawer() {
            mTrace = new ArrayList<GeoPoint>(100);
            mPolyline = new Polyline();
            mPolyline.setColor(0xAA0000FF);
            mPolyline.setWidth(2.0f);
            mPolyline.setGeodesic(true);
            osmMap.getOverlays().add(mPolyline);
        }

        @Override public void onMarkerDrag(Marker marker) {
            //mTrace.add(marker.getPosition());
        }

        @Override public void onMarkerDragEnd(Marker marker) {
            mTrace.add(marker.getPosition());
            mPolyline.setPoints(mTrace);
            osmMap.invalidate();
        }

        @Override public void onMarkerDragStart(Marker marker) {
            //mTrace.add(marker.getPosition());
        }
    }
}
