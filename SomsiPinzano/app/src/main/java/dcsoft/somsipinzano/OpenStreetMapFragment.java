package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class OpenStreetMapFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private MapView osmMap;
    private int zoom;
    private double lat;
    private double lon;
    private IMapController mapController;
    private ArrayList<Pdi> listaPdi;
    private int tipoMappa;
    private ArrayList<Marker> listaMarker;
    private FolderOverlay overlayTracciato;
    private Pdi pdiTracciatoAttivo;
    private static final OnlineTileSourceBase OpenCycleMap = new XYTileSource("OpenCycleMap",
            0, 17, 256, ".png?apikey=af1c6b2e71c041b398754a8b76eafe77", new String[] {
            "https://tile.thunderforest.com/cycle/"},
            "Maps © Thunderforest, Data © OpenStreetMap contributors.");
    private static final OnlineTileSourceBase Transport = new XYTileSource("Transport",
            0, 17, 256, ".png?apikey=af1c6b2e71c041b398754a8b76eafe77", new String[] {
            "https://tile.thunderforest.com/transport/"},
            "Maps © Thunderforest, Data © OpenStreetMap contributors.");
    private static final OnlineTileSourceBase Landscape = new XYTileSource("Landscape",
            0, 17, 256, ".png?apikey=af1c6b2e71c041b398754a8b76eafe77", new String[] {
            "https://tile.thunderforest.com/landscape/"},
            "Maps © Thunderforest, Data © OpenStreetMap contributors.");
    private static final OnlineTileSourceBase Outdoors = new XYTileSource("Outdoors",
            0, 17, 256, ".png?apikey=af1c6b2e71c041b398754a8b76eafe77", new String[] {
            "https://tile.thunderforest.com/outdoors/"},
            "Maps © Thunderforest, Data © OpenStreetMap contributors.");

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

        CompassOverlay compassOverlay = new CompassOverlay(mainActivity, new InternalCompassOrientationProvider(mainActivity), osmMap);
        compassOverlay.enableCompass();
        osmMap.getOverlays().add(compassOverlay);

        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(osmMap);
        locationOverlay.enableMyLocation();
        locationOverlay.setDrawAccuracyEnabled(true);
        osmMap.getOverlays().add(locationOverlay);

        mapController = osmMap.getController();

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
                InfoWindow.closeAllInfoWindowsOn(osmMap);

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint geoPoint) {
                return false;
            }
        };
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mapEventsReceiver);
        osmMap.getOverlays().add(mapEventsOverlay);

        if (savedInstanceState == null) {
            if (!zoommaSuPdiSceltoSeNecessario()) {
                mapController.setZoom(15);
                GeoPoint startPoint = new GeoPoint(46.1822, 12.9452);
                mapController.setCenter(startPoint);
            }

            tipoMappa = -1;

            pdiTracciatoAttivo = null;
        } else {
            //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null zoom: " + savedInstanceState.getInt("zoom") + " lat: " + savedInstanceState.getDouble("lat") + " lon: " + savedInstanceState.getDouble("lon") + " tipoMappa: " + savedInstanceState.getInt("tipoMappa") + " pdiTracciatoAttivo: " + savedInstanceState.getParcelable("pdiTracciatoAttivo"));

            zoom = savedInstanceState.getInt("zoom");
            lat  = savedInstanceState.getDouble("lat");
            lon  = savedInstanceState.getDouble("lon");

            if (!zoommaSuPdiSceltoSeNecessario()) {
                mapController.setZoom(zoom);
                GeoPoint startPoint = new GeoPoint(lat, lon);
                mapController.setCenter(startPoint);
            }

            tipoMappa = savedInstanceState.getInt("tipoMappa");

            pdiTracciatoAttivo = savedInstanceState.getParcelable("pdiTracciatoAttivo");
        }

        if (tipoMappa == 1) {
            osmMap.setTileSource(TileSourceFactory.MAPNIK);
        } else if (tipoMappa == 2) {
            osmMap.setTileSource(OpenCycleMap);
        } else if (tipoMappa == 3) {
            osmMap.setTileSource(TileSourceFactory.PUBLIC_TRANSPORT);
        }

        if (FirebaseHelper.dammiFirebaseHelperCondiviso().scaricamentoDatabaseRiuscitoConSuccesso()) {
            listaPdi = FirebaseHelper.dammiFirebaseHelperCondiviso().dammiPdi();
        } else {
            listaPdi = mainActivity.gestoreDatabaseCondiviso.dammiPdi();
        }
        listaMarker = new ArrayList<Marker>();

        for (int i = 0; i < listaPdi.size(); i++) {
            final Pdi pdi = listaPdi.get(i);

            GeoPoint posizione = new GeoPoint(pdi.getLatitudine(), pdi.getLongitudine());

            final Marker marker = new Marker(osmMap);
            marker.setPosition(posizione);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            Categoria categoria = null;
            if (pdi.getIdPdi_idCategoria() != null) {
                if (FirebaseHelper.dammiFirebaseHelperCondiviso().scaricamentoDatabaseRiuscitoConSuccesso()) {
                    categoria = FirebaseHelper.dammiFirebaseHelperCondiviso().dammiCategoria(pdi.getIdPdi_idCategoria());
                } else {
                    categoria = mainActivity.gestoreDatabaseCondiviso.dammiCategoria(pdi.getIdPdi_idCategoria());
                }
            }
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

                    gestisciTracciato(pdi, false);

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

        if (pdiTracciatoAttivo != null) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pdiTracciatoAttivo != null) {
                        impostaTracciatoSuMappa(pdiTracciatoAttivo, false);
                    } else {
                        Log.d("DEBUGAPP", TAG + " [onCreateView - handler] pdiTracciatoAttivo nullo! ");
                    }
                }
            }, 100);
        }

        return openStreetMapFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("zoom", osmMap.getZoomLevel());
        outState.putDouble("lat", osmMap.getMapCenter().getLatitude() < 80 ? osmMap.getMapCenter().getLatitude()  : lat);
        outState.putDouble("lon", osmMap.getMapCenter().getLongitude() > 0 ? osmMap.getMapCenter().getLongitude() : lon);
        outState.putInt("tipoMappa", tipoMappa);
        outState.putParcelable("pdiTracciatoAttivo", pdiTracciatoAttivo);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState zoom: " + osmMap.getZoomLevel() + " lat: " + osmMap.getMapCenter().getLatitude() + " lon: " + osmMap.getMapCenter().getLongitude() + " tipoMappa: " + tipoMappa + " pdiTracciatoAttivo: " + pdiTracciatoAttivo);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //Log.d("DEBUGAPP", TAG + " onHiddenChanged");

        zoommaSuPdiSceltoSeNecessario();
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

    private void impostaTracciatoSuMappa(final Pdi pdi, final Boolean zoom) {
        String urlFile = pdi.getUrlTracciaGps() + ".kmz";
        final String nomeFileKmz = pdi.getFileTracciaGps() + ".kmz";

        if (GestoreFileTracciatiGps.dammiGestoreDatabaseCondiviso(mainActivity).fileCacheNonEsiste(nomeFileKmz)) {
            Ion.with(mainActivity)
                    .load(urlFile)
                    .progress(new ProgressCallback() {@Override
                    public void onProgress(long downloaded, long total) {
                        Log.d("DEBUGAPP", TAG + " [impostaTracciatoSuMappa] errore: " + downloaded + " / " + total);
                    }
                    })
                    .write(new File(GestoreFileTracciatiGps.dammiGestoreDatabaseCondiviso(mainActivity).getGpsTracksPath() + nomeFileKmz))
                    .setCallback(new FutureCallback<File>() {
                        @Override
                        public void onCompleted(Exception e, File file) {
                            if (e != null) {
                                Log.d("DEBUGAPP", TAG + " Fallito download del file " + nomeFileKmz + " con errore: " + e);
                            } else {
                                impostaTracciatoSuMappaDaCache(pdi, nomeFileKmz, zoom);
                            }
                        }
                    });
        } else {
            impostaTracciatoSuMappaDaCache(pdi, nomeFileKmz, zoom);
        }
    }

    private void impostaTracciatoSuMappaDaCache(Pdi pdi, String nomeFileKmz, Boolean zoom) {
        if (GestoreFileTracciatiGps.dammiGestoreDatabaseCondiviso(mainActivity).fileCacheNonEsiste(nomeFileKmz)) {
            GestoreFileTracciatiGps.dammiGestoreDatabaseCondiviso(mainActivity).creaFileCache(nomeFileKmz);
        }

        File fileCache = GestoreFileTracciatiGps.dammiGestoreDatabaseCondiviso(mainActivity).getFileCache();

        try {
            InputStream inputStream = new FileInputStream(fileCache);
            File file = creaFileDaInputStream(inputStream, pdi.getFileTracciaGps());

            KmlDocument kmlDocument = new KmlDocument();
            kmlDocument.parseKMZFile(file);

            overlayTracciato = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(osmMap, null, null, kmlDocument);

            osmMap.getOverlays().add(overlayTracciato);
            osmMap.invalidate();

            if (zoom) {
                BoundingBox boundingBox = kmlDocument.mKmlRoot.getBoundingBox();
                osmMap.zoomToBoundingBox(boundingBox, true);
                osmMap.getController().setCenter(boundingBox.getCenter());
            }

            pdiTracciatoAttivo = pdi;
        } catch(Exception e) {
            Log.d("DEBUGAPP", TAG + " Fallita lettura del file " + nomeFileKmz + " con errore: " + e);

            overlayTracciato = null;
            pdiTracciatoAttivo = null;
        }
    }

    private void rimuoviTracciatoSeNecessario() {
        if (overlayTracciato != null) {
            pdiTracciatoAttivo = null;

            osmMap.getOverlays().remove(overlayTracciato);
            osmMap.invalidate();

            overlayTracciato = null;
        }
    }

    private void gestisciTracciato(Pdi pdi, Boolean zoom) {
        rimuoviTracciatoSeNecessario();

        if (pdi.getFileTracciaGps() != null) {
            impostaTracciatoSuMappa(pdi, zoom);
        }
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

                        if (mainActivity != null && mainActivity.pdiScelto != null) {
                            if (marker.getPosition().getLatitude() == mainActivity.pdiScelto.getLatitudine() && marker.getPosition().getLongitude() == mainActivity.pdiScelto.getLongitudine()) {
                                marker.showInfoWindow();

                                OsmdroidMarkerInfoWindow markerInfoWindow = (OsmdroidMarkerInfoWindow) marker.getInfoWindow();
                                gestisciTracciato(markerInfoWindow.pdi, true);

                                break;
                            }
                        } else {
                            Log.d("DEBUGAPP", TAG + " [zoommaSuPdiSceltoSeNecessario - handler] mainActivity.pdiScelto nullo! ");
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
        if (osmMap != null) {
            osmMap.setTileSource(TileSourceFactory.MAPNIK);
            tipoMappa = 1;
        }
    }

    public void impostaMappaCiclabile () {
        if (osmMap != null) {
            osmMap.setTileSource(OpenCycleMap);
            tipoMappa = 2;
        }
    }

    public void impostaMappaTrasporti () {
        if (osmMap != null) {
            osmMap.setTileSource(Transport);
            tipoMappa = 2;
        }
    }

    public void impostaMappaLandscape () {
        if (osmMap != null) {
            osmMap.setTileSource(Landscape);
            tipoMappa = 2;
        }
    }

    public void impostaMappaOutdoors () {
        if (osmMap != null) {
            osmMap.setTileSource(Outdoors);
            tipoMappa = 2;
        }
    }
    //endregion
}
