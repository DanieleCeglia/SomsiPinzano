package dcsoft.somsipinzano;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;

interface GoogleMapsFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private View googleMapsFragmentView;
    private MapView mMapView;
    private GoogleMap gmMap;
    private float zoom = -1;
    private double lat = -1;
    private double lon = -1;
    private int mapType = -1;
    private ArrayList<Pdi> listaPdi;

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

        googleMapsFragmentView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        mMapView = (MapView) googleMapsFragmentView.findViewById(R.id.gmMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // serve per far vedere la mappa immediatamente
        mMapView.getMapAsync(this);

        if (savedInstanceState != null) {
            Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            zoom    = savedInstanceState.getFloat("zoom");
            lat     = savedInstanceState.getDouble("lat");
            lon     = savedInstanceState.getDouble("lon");
            mapType = savedInstanceState.getInt("mapType");
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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
                alertDialogBuilder.setCancelable(true);

                alertDialogBuilder.setMessage(pdi.toString());

                alertDialogBuilder.setNeutralButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mainActivity);
                snippet.setTextColor(Color.GRAY);
                snippet.setGravity(Gravity.CENTER);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        listaPdi = mainActivity.gestoreDatabaseCondiviso.dammiPdi();

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
        }

        if (mapType != -1) {
            gmMap.setMapType(mapType);
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
