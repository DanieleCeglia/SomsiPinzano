package dcsoft.somsipinzano;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public GestoreDatabase gestoreDatabaseCondiviso;
    public BottomNavigationView bottomNavigation;
    public int tabSelezionato;
    public Categoria categoriaScelta;
    public RaggruppamentoPdi raggruppamentoPdiScelto;
    public Pdi pdiScelto;
    public Boolean vediPdiSceltoSuGM;
    public Boolean vediPdiSceltoSuOSM;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    public final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;

    private final String TAG = getClass().getSimpleName();
    private FragmentManager fragmentManager;
    private ActionBar actionBar;
    private CategoriaFragment categoriaFragment;
    private PdiFragment pdiFragment;
    private PdiDettaglioFragment pdiDettaglioFragment;
    private GoogleMapsFragment googleMapsFragment;
    private OpenStreetMapFragment openStreetMapFragment;
    private boolean toastGmVisualizzato;
    private boolean toastOsmVisualizzato;

    //region Metodi override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d("DEBUGAPP", TAG + " onCreate");

        gestoreDatabaseCondiviso = GestoreDatabase.dammiGestoreDatabaseCondiviso(this);

        controllaPermessi();

        setContentView(R.layout.activity_main);

        fragmentManager  = getFragmentManager();
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        actionBar        = getSupportActionBar();

        MenuItem menuSelezionato;

        if (savedInstanceState == null) {
            vediPdiSceltoSuGM  = false;
            vediPdiSceltoSuOSM = false;

            categoriaScelta         = null;
            raggruppamentoPdiScelto = null;
            pdiScelto               = null;

            categoriaFragment = new CategoriaFragment();
            pdiFragment       = null;

            tabSelezionato = 0;

            toastGmVisualizzato  = false;
            toastOsmVisualizzato = false;
        } else {
            //Log.d("DEBUGAPP", TAG + " onCreate savedInstanceState != null");

            vediPdiSceltoSuGM  = savedInstanceState.getBoolean("vediPdiSceltoSuGM");
            vediPdiSceltoSuOSM = savedInstanceState.getBoolean("vediPdiSceltoSuOSM");

            categoriaScelta         = savedInstanceState.getParcelable("categoriaScelta");
            raggruppamentoPdiScelto = savedInstanceState.getParcelable("raggruppamentoPdiScelto");
            pdiScelto               = savedInstanceState.getParcelable("pdiScelto");

            categoriaFragment     = (CategoriaFragment)     fragmentManager.findFragmentByTag("categoriaFragment");
            pdiFragment           = (PdiFragment)           fragmentManager.findFragmentByTag("pdiFragment");
            pdiDettaglioFragment  = (PdiDettaglioFragment)  fragmentManager.findFragmentByTag("pdiDettaglioFragment");
            googleMapsFragment    = (GoogleMapsFragment)    fragmentManager.findFragmentByTag("googleMapsFragment");
            openStreetMapFragment = (OpenStreetMapFragment) fragmentManager.findFragmentByTag("openStreetMapFragment");

            tabSelezionato = savedInstanceState.getInt("tabSelezionato");

            toastGmVisualizzato  = savedInstanceState.getBoolean("toastGmVisualizzato");
            toastOsmVisualizzato = savedInstanceState.getBoolean("toastOsmVisualizzato");
        }

        menuSelezionato = bottomNavigation.getMenu().getItem(tabSelezionato);

        if (googleMapsFragment == null) {
            googleMapsFragment = new GoogleMapsFragment();
        }
        googleMapsFragment.eseguiAlOnHiddenChanged = new GoogleMapsFragmentEseguiAlOnHiddenChanged() {
            @Override
            public void esegui(boolean hidden) {
                //if (!hidden) {
                //    Log.d("DEBUGAPP", TAG + " GoogleMapsFragmentEseguiAlOnHiddenChanged");
                //}
            }
        };

        if (openStreetMapFragment == null) {
            openStreetMapFragment = new OpenStreetMapFragment();
        }
        openStreetMapFragment.eseguiAlOnHiddenChanged = new OpenStreetMapFragmentEseguiAlOnHiddenChanged() {
            @Override
            public void esegui(boolean hidden) {
                //if (!hidden) {
                //    Log.d("DEBUGAPP", TAG + " OpenStreetMapFragmentEseguiAlOnHiddenChanged");
                //}
            }
        };

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                attivaTab(item, false);
                return true;
            }
        });

        attivaTab(menuSelezionato, true);
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
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.messaggio_permessi_accettati), Toast.LENGTH_LONG).show();
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

            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pdiDettaglioFragment.chiamaNumeroTelefonico(null);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setCancelable(true);

                    alertDialogBuilder.setMessage(getResources().getString(R.string.messaggio_permesso_di_chiamata_non_accettato));

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

            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        savedInstanceState.putBoolean("vediPdiSceltoSuGM", vediPdiSceltoSuGM);
        savedInstanceState.putBoolean("vediPdiSceltoSuOSM", vediPdiSceltoSuOSM);
        savedInstanceState.putInt("tabSelezionato", tabSelezionato);
        savedInstanceState.putBoolean("toastGmVisualizzato", toastGmVisualizzato);
        savedInstanceState.putBoolean("toastOsmVisualizzato", toastOsmVisualizzato);
        savedInstanceState.putParcelable("categoriaScelta", categoriaScelta);
        savedInstanceState.putParcelable("raggruppamentoPdiScelto", raggruppamentoPdiScelto);
        savedInstanceState.putParcelable("pdiScelto", pdiScelto);
    }

    @Override
    public void onBackPressed() {
        if (pdiDettaglioFragment != null && pdiDettaglioFragment.isVisible()) {
            rimuoviPdiDettaglioFragment();
        } else if (pdiFragment != null && pdiFragment.isVisible()) {
            gestisciBackSuPdiFragment();
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
                    gestisciBackSuPdiFragment();
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
            //break;
            //
            //case R.id.menuMappaTrasportiOSM: {
            //    openStreetMapFragment.impostaMappaTrasporti();
            //}
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tabSelezionato == 1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_gm, menu);

            return true;
        }

        if (tabSelezionato == 2) {
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
            impostaSottotitoloActionBar();
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

    public void raggruppamentoPdiScelto(RaggruppamentoPdi raggruppamentoPdi) {
        raggruppamentoPdiScelto = raggruppamentoPdi;
        impostaSottotitoloActionBar();
        pdiFragment.ricarica();
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
        MenuItem menuSelezionato = bottomNavigation.getMenu().getItem(1);
        attivaTab(menuSelezionato, false);
    }

    public void vediPdiSceltoSuOSM() {
        vediPdiSceltoSuOSM = true;
        MenuItem menuSelezionato = bottomNavigation.getMenu().getItem(2);
        attivaTab(menuSelezionato, false);
    }

    public void apriDettaglioSuPdi(Pdi pdi) {
        MenuItem menuSelezionato = bottomNavigation.getMenu().getItem(0);
        attivaTab(menuSelezionato, false);

        if (pdiDettaglioFragment != null) {
            rimuoviPdiDettaglioFragment();
            rimuoviPdiFragment();
        } else if (pdiFragment != null) {
            rimuoviPdiFragment();
        }

        Categoria categoria = gestoreDatabaseCondiviso.dammiCategoria(pdi.getIdPdi_idCategoria());
        categoriaScelta(categoria);

        RaggruppamentoPdi raggruppamentoPdi = gestoreDatabaseCondiviso.dammiRaggruppamentoPdi(pdi.getIdPdi_idRaggruppamento());
        raggruppamentoPdiScelto(raggruppamentoPdi);

        pdiScelto(pdi);
    }
    //endregion

    //region Metodi privati
    private void controllaPermessi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // se è Android 6.0 o superiore...
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

    private void attivaTab(MenuItem item, Boolean eseguiForzatamente) {
        int itemId = item.getItemId();

        if (!((tabSelezionato == 0 && itemId == R.id.item_pdi) || (tabSelezionato == 1 && itemId == R.id.item_google_maps) || (tabSelezionato == 2 && itemId == R.id.item_open_street_map)) || eseguiForzatamente) { // evito di eseguire codice inutilmente (salvo forzature)...
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

            // ... e imposto tutti i tab come non selezionati...
            bottomNavigation.getMenu().getItem(0).setChecked(false);
            bottomNavigation.getMenu().getItem(1).setChecked(false);
            bottomNavigation.getMenu().getItem(2).setChecked(false);

            // poi vedo chi mostrare o addirittura aggiungere...
            switch (itemId) {
                case R.id.item_pdi: {
                    tabSelezionato = 0;

                    if (pdiDettaglioFragment != null && pdiDettaglioFragment.isAdded()) {
                        fragmentTransaction.show(pdiDettaglioFragment);

                        switch (gestoreDatabaseCondiviso.getLingua()) {
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

                        switch (gestoreDatabaseCondiviso.getLingua()) {
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
                    tabSelezionato = 1;

                    if (googleMapsFragment != null && googleMapsFragment.isAdded()) {
                        fragmentTransaction.show(googleMapsFragment);
                    } else {
                        fragmentTransaction.add(R.id.contentContainer, googleMapsFragment, "googleMapsFragment");
                    }

                    impostaActionBar(false, "Google Maps");
                }
                break;

                case R.id.item_open_street_map: {
                    tabSelezionato = 2;

                    if (openStreetMapFragment != null && openStreetMapFragment.isAdded()) {
                        fragmentTransaction.show(openStreetMapFragment);
                    } else {
                        fragmentTransaction.add(R.id.contentContainer, openStreetMapFragment, "openStreetMapFragment");
                    }

                    impostaActionBar(false, "OpenStreetMap");
                }
            }

            bottomNavigation.getMenu().getItem(tabSelezionato).setChecked(true);

            invalidateOptionsMenu(); // dico di aggiornare l'action bar (per GM e OSM che hanno il menu a puntini)

            fragmentTransaction.commit();

            if (tabSelezionato == 1 && !toastGmVisualizzato) {
                Toast.makeText(MainActivity.this,  getResources().getString(R.string.tocca_pin_per_maggiori_dettagli), Toast.LENGTH_LONG).show();
                toastGmVisualizzato = true;
            }

            if (tabSelezionato == 2 && !toastOsmVisualizzato) {
                Toast.makeText(MainActivity.this,  getResources().getString(R.string.tocca_pin_per_maggiori_dettagli), Toast.LENGTH_LONG).show();
                toastOsmVisualizzato = true;
            }
        }
    }

    private void rimuoviPdiFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(pdiFragment);
        fragmentTransaction.show(categoriaFragment);
        fragmentTransaction.commit();

        pdiFragment = null;

        categoriaScelta = null;

        impostaActionBar(false, getResources().getString(R.string.Categorie));
    }

    private void rimuoviPdiDettaglioFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(pdiDettaglioFragment);
        fragmentTransaction.show(pdiFragment);
        fragmentTransaction.commit();

        pdiDettaglioFragment = null;

        pdiScelto = null;

        switch (gestoreDatabaseCondiviso.getLingua()) {
            case "italiano": {
                impostaActionBar(true, categoriaScelta.getNomeItaliano());
            }
            break;

            default: {
                impostaActionBar(true, categoriaScelta.getNomeInglese());
            }
        }
    }

    private void impostaSottotitoloActionBar() {
        if (raggruppamentoPdiScelto != null && tabSelezionato == 0) {
            switch (gestoreDatabaseCondiviso.getLingua()) {
                case "italiano": {
                    actionBar.setSubtitle(raggruppamentoPdiScelto.getNomeRaggruppamentoItaliano());
                }
                break;

                default: {
                    actionBar.setSubtitle(raggruppamentoPdiScelto.getNomeRaggruppamentoInglese());
                }
            }
        } else {
            actionBar.setSubtitle(null);
        }
    }

    private void gestisciBackSuPdiFragment() {
        if (raggruppamentoPdiScelto != null) {
            raggruppamentoPdiScelto = null;
            impostaSottotitoloActionBar();
            pdiFragment.ricarica();
        } else {
            rimuoviPdiFragment();
        }
    }
    //endregion
}
