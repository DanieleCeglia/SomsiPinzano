package dcsoft.somsipinzano;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public DatabaseAdapter databaseAdapter;
    public BottomBar bottomBar;
    public Categoria categoriaScelta;
    public Pdi pdiScelto;
    public Boolean vediPdiSceltoSuGM;
    public Boolean vediPdiSceltoSuOSM;

    private static final String TAG = "MainActivity ";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private CategoriaFragment categoriaFragment;
    private PdiFragment pdiFragment;
    private PdiDettaglioFragment pdiDettaglioFragment;
    private GoogleMapsFragment googleMapsFragment;
    private OpenStreetMapFragment openStreetMapFragment;

    //region Metodi override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d("DEBUGAPP", TAG + "onCreate");

        databaseAdapter = DatabaseAdapter.dammiDbHelperCondiviso(this);

        controllaPermessi();

        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        actionBar = getSupportActionBar();

        if (savedInstanceState == null) {
            vediPdiSceltoSuGM  = false;
            vediPdiSceltoSuOSM = false;

            categoriaScelta = null;

            categoriaFragment     = new CategoriaFragment();
            pdiFragment           = null;
        } else {
            //Log.d("DEBUGAPP", TAG + "onCreate savedInstanceState != null");

            vediPdiSceltoSuGM  = savedInstanceState.getBoolean("vediPdiSceltoSuGM");
            vediPdiSceltoSuOSM = savedInstanceState.getBoolean("vediPdiSceltoSuOSM");

            categoriaScelta = (Categoria) savedInstanceState.getParcelable("categoriaScelta");
            pdiScelto       = (Pdi)       savedInstanceState.getParcelable("pdiScelto");

            categoriaFragment     = (CategoriaFragment)     fragmentManager.findFragmentByTag("categoriaFragment");
            pdiFragment           = (PdiFragment)           fragmentManager.findFragmentByTag("pdiFragment");
            pdiDettaglioFragment  = (PdiDettaglioFragment)  fragmentManager.findFragmentByTag("pdiDettaglioFragment");
            googleMapsFragment    = (GoogleMapsFragment)    fragmentManager.findFragmentByTag("googleMapsFragment");
            openStreetMapFragment = (OpenStreetMapFragment) fragmentManager.findFragmentByTag("openStreetMapFragment");

            bottomBar.setDefaultTabPosition(savedInstanceState.getInt("currentTabPosition"));
        }

        if (googleMapsFragment == null) {
            googleMapsFragment = new GoogleMapsFragment();
        }
        googleMapsFragment.eseguiAlOnHiddenChanged = new GoogleMapsFragmentEseguiAlOnHiddenChanged() {
            @Override
            public void esegui(boolean hidden) {
                if (!hidden) {
                    //Log.d("DEBUGAPP", TAG + "GoogleMapsFragmentEseguiAlOnHiddenChanged");
                }
            }
        };

        if (openStreetMapFragment == null) {
            openStreetMapFragment = new OpenStreetMapFragment();
        }
        openStreetMapFragment.eseguiAlOnHiddenChanged = new OpenStreetMapFragmentEseguiAlOnHiddenChanged() {
            @Override
            public void esegui(boolean hidden) {
                if (!hidden) {
                    //Log.d("DEBUGAPP", TAG + "OpenStreetMapFragmentEseguiAlOnHiddenChanged");
                }
            }
        };

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                attivaTab(tabId);
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                attivaTab(tabId);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();

                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }

                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (location && storage) {
                    Toast.makeText(MainActivity.this,  getResources().getString(R.string.messaggio_permessi_accettati), Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setCancelable(true);

                    if (location) {
                        alertDialogBuilder.setMessage(getResources().getString(R.string.messaggio_permesso_di_archiviazione_non_accettato));
                    } else if (storage) {
                        alertDialogBuilder.setMessage(getResources().getString(R.string.messaggio_permesso_di_localizzazione_non_accettato));
                    } else {
                        alertDialogBuilder.setMessage(getResources().getString(R.string.messaggio_permesso_di_archiviazione_non_accettato)
                                + "\n\n"
                                + getResources().getString(R.string.messaggio_permesso_di_localizzazione_non_accettato));
                    }

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
            }
            break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        savedInstanceState.putBoolean("vediPdiSceltoSuGM", vediPdiSceltoSuGM);
        savedInstanceState.putBoolean("vediPdiSceltoSuOSM", vediPdiSceltoSuOSM);
        savedInstanceState.putInt("currentTabPosition", bottomBar.getCurrentTabPosition());
        savedInstanceState.putParcelable("categoriaScelta", categoriaScelta);
        savedInstanceState.putParcelable("pdiScelto", pdiScelto);
    }

    @Override
    public void onBackPressed() {
        if (pdiDettaglioFragment != null && pdiDettaglioFragment.isVisible()) {
            rimuoviPdiDettaglioFragment();
        } else if (pdiFragment != null && pdiFragment.isVisible()) {
            rimuoviPdiFragment();
        } else {
            this.finishAffinity();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (pdiDettaglioFragment != null && pdiDettaglioFragment.isVisible()) {
                    rimuoviPdiDettaglioFragment();
                } else {
                    rimuoviPdiFragment();
                }
            }
            break;

            case R.id.menuMappaNormleGM: {
                googleMapsFragment.impostaMappaNormale();
            }
            break;

            case R.id.menuMappaSatellitareGM: {
                googleMapsFragment.impostaMappaSatellitare();
            }
            break;

            case R.id.menuMappaIbridaGM: {
                googleMapsFragment.impostaMappaIbrida();
            }
            break;

            case R.id.menuMappaRilievoGM: {
                googleMapsFragment.impostaMapparilievo();
            }
            break;

            case R.id.menuMappaNormleOSM: {
                openStreetMapFragment.impostaMappaNormale();
            }
            break;

            case R.id.menuMappaCiclabileOSM: {
                openStreetMapFragment.impostaMappaCiclabile();
            }
            break;

            case R.id.menuMappaTrasportiOSM: {
                openStreetMapFragment.impostaMappaTrasporti();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bottomBar.getCurrentTabPosition() == 1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_gm, menu);

            return true;
        }

        if (bottomBar.getCurrentTabPosition() == 2) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_osm, menu);

            return true;
        }

        return false;
    }
    //endregion

    //region Metodi pubblici
    public void impostaActionBar(boolean backAttivo, String titolo) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(backAttivo);
            actionBar.setTitle(titolo);
        }
    }

    public void categoriaScelta(Categoria categoria) {
        categoriaScelta = categoria;
        pdiFragment = new PdiFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(categoriaFragment);
        fragmentTransaction.add(R.id.contentContainer, pdiFragment, "pdiFragment");
        fragmentTransaction.commit();
    }

    public void pdiScelto(Pdi pdi) {
        pdiScelto = pdi;
        pdiDettaglioFragment = new PdiDettaglioFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(pdiFragment);
        fragmentTransaction.add(R.id.contentContainer, pdiDettaglioFragment, "pdiDettaglioFragment");
        fragmentTransaction.commit();
    }

    public void vediPdiSceltoSuGM() {
        vediPdiSceltoSuGM = true;
        bottomBar.selectTabAtPosition(1);
    }


    public void vediPdiSceltoSuOSM() {
        vediPdiSceltoSuOSM = true;
        bottomBar.selectTabAtPosition(2);
    }
    //endregion

    //region Metodi privati
    private void controllaPermessi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// se è Android 6.0 o superiore...
            List<String> permessiDaRichiedere = new ArrayList<>();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permessiDaRichiedere.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permessiDaRichiedere.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!permessiDaRichiedere.isEmpty()) {
                requestPermissions(permessiDaRichiedere.toArray(new String[permessiDaRichiedere.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        } // else: ci sono già i permessi, quindi non c'è niente da fare!
    }

    private void attivaTab(int tabId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // per prima cosa nascondo tutti i fragment (se esistenti e già aggiunti)...
        if (categoriaFragment != null && categoriaFragment.isAdded()) {
            fragmentTransaction.hide(categoriaFragment);
        }
        if (pdiFragment != null && pdiFragment.isAdded()) {
            fragmentTransaction.hide(pdiFragment);
        }
        if (pdiDettaglioFragment != null && pdiDettaglioFragment.isAdded()) {
            fragmentTransaction.hide(pdiDettaglioFragment);
        }
        if (googleMapsFragment != null && googleMapsFragment.isAdded()) {
            fragmentTransaction.hide(googleMapsFragment);
        }
        if (openStreetMapFragment != null && openStreetMapFragment.isAdded()) {
            fragmentTransaction.hide(openStreetMapFragment);
        }

        // poi vedo chi mostrare o addirittura aggiungere...
        switch (tabId) {
            case R.id.item_pdi: {
                if (pdiDettaglioFragment != null && pdiDettaglioFragment.isAdded()) {
                    fragmentTransaction.show(pdiDettaglioFragment);

                    switch (databaseAdapter.getLingua()) {
                        case "italiano": {
                            impostaActionBar(true, pdiScelto.getTitoloItaliano());
                        }
                        break;

                        default: {
                            impostaActionBar(true, pdiScelto.getTitoloInglese());
                        }
                    }
                } else if (pdiFragment != null && pdiFragment.isAdded()) {
                    fragmentTransaction.show(pdiFragment);

                    switch (databaseAdapter.getLingua()) {
                        case "italiano": {
                            impostaActionBar(true, categoriaScelta.getNomeItaliano());
                        }
                        break;

                        default: {
                            impostaActionBar(true, categoriaScelta.getNomeInglese());
                        }
                    }
                } else {
                    if (categoriaFragment != null && categoriaFragment.isAdded()) {
                        fragmentTransaction.show(categoriaFragment);
                    } else {
                        fragmentTransaction.add(R.id.contentContainer, categoriaFragment, "categoriaFragment");
                    }

                    impostaActionBar(false, getResources().getString(R.string.Categorie));
                }
            }
            break;

            case R.id.item_google_maps: {
                if (googleMapsFragment != null && googleMapsFragment.isAdded()) {
                    fragmentTransaction.show(googleMapsFragment);
                } else {
                    fragmentTransaction.add(R.id.contentContainer, googleMapsFragment, "googleMapsFragment");
                }

                impostaActionBar(false, "Google Maps");
            }
            break;

            case R.id.item_open_street_map: {
                if (openStreetMapFragment != null && openStreetMapFragment.isAdded()) {
                    fragmentTransaction.show(openStreetMapFragment);
                } else {
                    fragmentTransaction.add(R.id.contentContainer, openStreetMapFragment, "openStreetMapFragment");
                }

                impostaActionBar(false, "OpenStreetMap");
            }
        }

        fragmentTransaction.commit();

        invalidateOptionsMenu();
    }

    private void rimuoviPdiFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(pdiFragment);
        fragmentTransaction.show(categoriaFragment);
        fragmentTransaction.commit();

        pdiFragment = null;

        impostaActionBar(false, getResources().getString(R.string.Categorie));
    }

    private void rimuoviPdiDettaglioFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(pdiDettaglioFragment);
        fragmentTransaction.show(pdiFragment);
        fragmentTransaction.commit();

        pdiDettaglioFragment = null;

        switch (databaseAdapter.getLingua()) {
            case "italiano": {
                impostaActionBar(true, categoriaScelta.getNomeItaliano());
            }
            break;

            default: {
                impostaActionBar(true, categoriaScelta.getNomeInglese());
            }
        }
    }
    //endregion
}
