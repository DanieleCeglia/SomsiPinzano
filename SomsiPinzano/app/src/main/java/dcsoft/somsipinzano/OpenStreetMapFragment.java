package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

interface OpenStreetMapFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class OpenStreetMapFragment extends Fragment implements MapEventsReceiver {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private MapView osmMap;
    private IMapController mapController;
    private ArrayList<Pdi> listaPdi;
    private int tipoMappa;
    private ArrayList<Marker> listaMarker;
    private FolderOverlay overlayTracciato;

    public OpenStreetMapFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public OpenStreetMapFragment() {
        // Required empty public constructor
    }

    //region Metodi override
    @Override
    public void onAttach(Activity activity) { // per API < 23
        super.onAttach(activity);

        //Log.d("DEBUGAPP", TAG + " onAttach");

        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Log.d("DEBUGAPP", TAG + " onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + " onCreateView");

        View openStreetMapFragmentView = inflater.inflate(R.layout.fragment_open_street_map, container, false);

        osmMap = (MapView) openStreetMapFragmentView.findViewById(R.id.osmMap);
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        osmMap.setBuiltInZoomControls(true);
        osmMap.setMultiTouchControls(true);
        osmMap.setTilesScaledToDpi(true);

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mainActivity, this);
        osmMap.getOverlays().add(0, mapEventsOverlay);

        CompassOverlay compassOverlay = new CompassOverlay(mainActivity, new InternalCompassOrientationProvider(mainActivity), osmMap);
        compassOverlay.enableCompass();
        osmMap.getOverlays().add(compassOverlay);

        //GpsMyLocationProvider gpsLocationProvider = new GpsMyLocationProvider(mainActivity);
        //gpsLocationProvider.setLocationUpdateMinTime(1000);
        //gpsLocationProvider.setLocationUpdateMinDistance(1);

        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(mainActivity), osmMap);
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

            tipoMappa = -1;
        } else {
            Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            if (!zoommaSuPdiSceltoSeNecessario()) {
                mapController.setZoom(savedInstanceState.getInt("zoom"));
                GeoPoint startPoint = new GeoPoint(savedInstanceState.getDouble("lat"), savedInstanceState.getDouble("lon"));
                mapController.setCenter(startPoint);
            }

            tipoMappa = savedInstanceState.getInt("tipoMappa");
        }

        if (tipoMappa == 1) {
            osmMap.setTileSource(TileSourceFactory.MAPNIK);
        } else if (tipoMappa == 2) {
            osmMap.setTileSource(TileSourceFactory.CYCLEMAP);
        } else if (tipoMappa == 3) {
            osmMap.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT);
        }

        listaPdi = mainActivity.gestoreDatabaseCondiviso.dammiPdi();
        listaMarker = new ArrayList<Marker>();

        for (int i = 0; i < listaPdi.size(); i++) {
            Pdi pdi = listaPdi.get(i);

            GeoPoint posizione = new GeoPoint(pdi.getLatitudine(), pdi.getLongitudine());

            Marker marker = new Marker(osmMap);
            marker.setPosition(posizione);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            Categoria categoria = mainActivity.gestoreDatabaseCondiviso.dammiCategoria(pdi.getIdPdi_idCategoria());
            if (categoria != null) {
                String nomeFileSenzaEstensione = categoria.getFilePin().substring(0, categoria.getFilePin().lastIndexOf('.'));
                String packageName = mainActivity.getPackageName();

                marker.setIcon(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName), null));
            }

            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker item, MapView arg1) {
                    InfoWindow.closeAllInfoWindowsOn(osmMap);
                    item.showInfoWindow();
                    mapController.animateTo(item.getPosition());

                    return true;
                }
            });

            OsmdroidMarkerInfoWindow markerInfoWindow = new OsmdroidMarkerInfoWindow(R.layout.osmdroid_marker_info_window, osmMap);
            markerInfoWindow.mainActivity = mainActivity;
            markerInfoWindow.pdi          = pdi;
            marker.setInfoWindow(markerInfoWindow);

            osmMap.getOverlays().add(marker);

            listaMarker.add(marker);
        }

        return openStreetMapFragmentView;
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
        InfoWindow.closeAllInfoWindowsOn(osmMap);

        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint geoPoint) {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        outState.putInt("zoom", osmMap.getZoomLevel());
        outState.putDouble("lat", osmMap.getMapCenter().getLatitude());
        outState.putDouble("lon", osmMap.getMapCenter().getLongitude());
        outState.putInt("tipoMappa", tipoMappa);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //Log.d("DEBUGAPP", TAG + " onHiddenChanged");

        zoommaSuPdiSceltoSeNecessario();

        if (eseguiAlOnHiddenChanged != null) {
            eseguiAlOnHiddenChanged.esegui(hidden);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + " onDetach");

        mainActivity = null;
    }
    //endregion

    //region Metodi privati
    private File creaFileDaInputStream(InputStream inputStream, String nomeFile) {
        String percorsoFile = mainActivity.getApplicationInfo().dataDir + "/" + nomeFile;

        try {
            File file = new File(percorsoFile);
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int lunghezza;

            while ((lunghezza = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, lunghezza);
            }

            outputStream.close();
            inputStream.close();

            return file;
        } catch (IOException e) {
            Log.d("DEBUGAPP", TAG + " Fallita la copia del file " + percorsoFile + " con errore: " + e);
        }

        return null;
    }

    private boolean zoommaSuPdiSceltoSeNecessario() {
        if (mainActivity.vediPdiSceltoSuOSM) {
            mainActivity.vediPdiSceltoSuOSM = false;

            InfoWindow.closeAllInfoWindowsOn(osmMap);

            mapController.setZoom(18);
            GeoPoint startPoint = new GeoPoint(mainActivity.pdiScelto.getLatitudine(), mainActivity.pdiScelto.getLongitudine());
            mapController.setCenter(startPoint);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < listaMarker.size(); i++) {
                        Marker marker = listaMarker.get(i);

                        if (marker.getPosition().getLatitude() == mainActivity.pdiScelto.getLatitudine() && marker.getPosition().getLongitude() == mainActivity.pdiScelto.getLongitudine()) {
                            marker.showInfoWindow();

                            OsmdroidMarkerInfoWindow markerInfoWindow = (OsmdroidMarkerInfoWindow) marker.getInfoWindow();

                            if (markerInfoWindow.pdi.getFileTracciaGps() == null) {
                                if (overlayTracciato != null) {
                                    osmMap.getOverlays().remove(overlayTracciato);
                                    osmMap.invalidate();
                                }
                            } else  {
                                try {
                                    InputStream inputStream = mainActivity.getAssets().open(markerInfoWindow.pdi.getFileTracciaGps());
                                    File file = creaFileDaInputStream(inputStream, markerInfoWindow.pdi.getFileTracciaGps());

                                    KmlDocument kmlDocument = new KmlDocument();
                                    kmlDocument.parseKMZFile(file);

                                    overlayTracciato = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(osmMap, null, null, kmlDocument);

                                    osmMap.getOverlays().add(overlayTracciato);
                                    osmMap.invalidate();

                                    BoundingBox boundingBox = kmlDocument.mKmlRoot.getBoundingBox();
                                    osmMap.zoomToBoundingBox(boundingBox, true);
                                    osmMap.getController().setCenter(boundingBox.getCenter());
                                } catch(IOException e) {
                                    Log.d("DEBUGAPP", TAG + " Fallita lettura del file " + markerInfoWindow.pdi.getFileTracciaGps() + " con errore: " + e);
                                }
                            }

                            break;
                        }
                    }
                }
            }, 100);

            return true;
        }

        return false;
    }
    //endregion

    //region Metodi pubblici
    public void impostaMappaNormale () {
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        tipoMappa = 1;
    }

    public void impostaMappaCiclabile () {
        osmMap.setTileSource(TileSourceFactory.CYCLEMAP);
        tipoMappa = 2;
    }

    public void impostaMappaTrasporti () {
        osmMap.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT);
        tipoMappa = 2;
    }
    //endregion
}
