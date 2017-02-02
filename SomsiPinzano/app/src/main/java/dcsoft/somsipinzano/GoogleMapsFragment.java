package dcsoft.somsipinzano;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

interface GoogleMapsFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private GoogleMap gmMap;
    private float zoom;
    private double lat;
    private double lon;
    private int mapType;
    private ArrayList<Pdi> listaPdi;
    private ArrayList<Marker> listaMarker;
    private KmlLayer kmlLayer;
    private Pdi pdiTracciatoAttivo;

    public GoogleMapsFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public GoogleMapsFragment() {
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

        View googleMapsFragmentView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        MapView mMapView = (MapView) googleMapsFragmentView.findViewById(R.id.gmMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // serve per far vedere la mappa immediatamente
        mMapView.getMapAsync(this);

        if (savedInstanceState == null) {
            zoom = -1;
            lat = -1;
            lon = -1;
            mapType = -1;

            pdiTracciatoAttivo = null;
        } else {
            Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            zoom    = savedInstanceState.getFloat("zoom");
            lat     = savedInstanceState.getDouble("lat");
            lon     = savedInstanceState.getDouble("lon");
            mapType = savedInstanceState.getInt("mapType");

            pdiTracciatoAttivo = savedInstanceState.getParcelable("pdiTracciatoAttivo");
        }

        return googleMapsFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        if (gmMap != null) {
            CameraPosition cameraPosition = gmMap.getCameraPosition();

            if (cameraPosition != null) {
                outState.putFloat("zoom", gmMap.getCameraPosition().zoom);
                outState.putDouble("lat", gmMap.getCameraPosition().target.latitude);
                outState.putDouble("lon", gmMap.getCameraPosition().target.longitude);
            }

            outState.putInt("mapType", gmMap.getMapType());
            outState.putParcelable("pdiTracciatoAttivo", pdiTracciatoAttivo);
        }
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
    public void onMapReady(GoogleMap googleMap) {
        //Log.d("DEBUGAPP", TAG + " onMapReady");

        gmMap = googleMap;

        gmMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Pdi pdi = (Pdi) marker.getTag();

                mainActivity.apriDettaglioSuPdi(pdi);
            }
        });

        gmMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(mainActivity);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mainActivity);
                title.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorPrimaryText));
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mainActivity);
                snippet.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorSecondaryText));
                snippet.setGravity(Gravity.CENTER);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                gestisciTracciato(marker, false);

                return info;
            }
        });

        listaPdi = mainActivity.gestoreDatabaseCondiviso.dammiPdi();
        listaMarker = new ArrayList<Marker>();

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

            Categoria categoria = mainActivity.gestoreDatabaseCondiviso.dammiCategoria(pdi.getIdPdi_idCategoria());
            if (categoria != null) {
                String nomeFileSenzaEstensione = categoria.getFilePin().substring(0, categoria.getFilePin().lastIndexOf('.'));
                String packageName = mainActivity.getPackageName();

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));

                markerOptions.icon(icon);
            }

            switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
                case "italiano": {
                    markerOptions.title(pdi.getTitoloItaliano());
                }
                break;

                default: {
                    markerOptions.title(pdi.getTitoloInglese());
                }
            }

            markerOptions.snippet(getResources().getString(R.string.tocca_qui_per_maggiori_dettagli));

            Marker marker = gmMap.addMarker(markerOptions);
            marker.setTag(pdi);

            listaMarker.add(marker);
        }

        if (mapType != -1) {
            gmMap.setMapType(mapType);
        }

        if (pdiTracciatoAttivo != null) {
            impostaTracciatoSuMappa(pdiTracciatoAttivo, false);
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
    private void impostaTracciatoSuMappa(Pdi pdi, Boolean zoom) {
        String nomeFileKml = pdi.getFileTracciaGps() + ".kml";

        try {
            InputStream inputStream = mainActivity.getAssets().open(nomeFileKml);

            kmlLayer = new KmlLayer(gmMap, inputStream, mainActivity.getApplicationContext());
            kmlLayer.addLayerToMap();

            if (zoom) {
                gmMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            }

            pdiTracciatoAttivo = pdi;
        } catch(IOException e) {
            Log.d("DEBUGAPP", TAG + " Fallita lettura del file " + nomeFileKml + " con errore: " + e);

            kmlLayer = null;
            pdiTracciatoAttivo = null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d("DEBUGAPP", TAG + " Fallito parsing XML del file " + nomeFileKml + " con errore: " + e);

            kmlLayer = null;
            pdiTracciatoAttivo = null;
        }
    }

    private void rimuoviTracciatoSeNecessario() {
        if (kmlLayer != null) {
            pdiTracciatoAttivo = null;

            kmlLayer.removeLayerFromMap();

            kmlLayer = null;
        }
    }

    private void gestisciTracciato(Marker marker, Boolean zoom) {
        Pdi pdi = (Pdi) marker.getTag();

        if (pdi.getFileTracciaGps() == null) {
            rimuoviTracciatoSeNecessario();
        } else {
            impostaTracciatoSuMappa(pdi, zoom);
        }
    }

    private boolean zoommaSuPdiSceltoSeNecessario() {
        if (mainActivity.vediPdiSceltoSuGM && gmMap != null) {
            mainActivity.vediPdiSceltoSuGM = false;

            LatLng pdiDaZoommare = new LatLng(mainActivity.pdiScelto.getLatitudine(), mainActivity.pdiScelto.getLongitudine());

            gmMap.moveCamera(CameraUpdateFactory.newLatLng(pdiDaZoommare));
            gmMap.animateCamera(CameraUpdateFactory.zoomTo(18));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < listaMarker.size(); i++) {
                        Marker marker = listaMarker.get(i);

                        if (marker.getPosition().latitude == mainActivity.pdiScelto.getLatitudine() && marker.getPosition().longitude == mainActivity.pdiScelto.getLongitudine()) {
                            marker.showInfoWindow();

                            gestisciTracciato(marker, true);
                        } else {
                            marker.hideInfoWindow();
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
        gmMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void impostaMappaSatellitare () {
        gmMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void impostaMappaIbrida () {
        gmMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void impostaMapparilievo () {
        gmMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
    //endregion
}
