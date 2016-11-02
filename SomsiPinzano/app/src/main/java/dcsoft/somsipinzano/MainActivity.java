package dcsoft.somsipinzano;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    public BottomBar bottomBar;
    public Categoria categoriaScelta;
    public Pdi pdiScelto;

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

        controllaPermessi();

        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        actionBar = getSupportActionBar();

        if (savedInstanceState == null) {
            categoriaScelta = null;

            categoriaFragment     = new CategoriaFragment();
            pdiFragment           = null;
        } else {
            //Log.d("DEBUGAPP", TAG + "onCreate savedInstanceState != nul");

            categoriaScelta = (Categoria) savedInstanceState.getSerializable("categoriaScelta");
            pdiScelto       = (Pdi)       savedInstanceState.getSerializable("pdiScelto");

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
                    Log.d("DEBUGAPP", TAG + "GoogleMapsFragmentEseguiAlOnHiddenChanged");
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
                    Log.d("DEBUGAPP", TAG + "OpenStreetMapFragmentEseguiAlOnHiddenChanged");
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
                } else if (location) {
                    Toast.makeText(this, getResources().getString(R.string.messaggio_permesso_di_archiviazione_non_accettato), Toast.LENGTH_LONG).show();
                } else if (storage) {
                    Toast.makeText(this, getResources().getString(R.string.messaggio_permesso_di_localizzazione_non_accettato), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.messaggio_permesso_di_archiviazione_non_accettato) + "\n\n"
                            + getResources().getString(R.string.messaggio_permesso_di_localizzazione_non_accettato), Toast.LENGTH_LONG).show();
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

        savedInstanceState.putSerializable("categoriaScelta", categoriaScelta);
        savedInstanceState.putSerializable("pdiScelto", pdiScelto);
        savedInstanceState.putInt("currentTabPosition", bottomBar.getCurrentTabPosition());
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

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
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

    private void nascondiCategoriaFragment(FragmentTransaction fragmentTransaction) {
        if (categoriaFragment != null && categoriaFragment.isAdded()) {
            fragmentTransaction.hide(categoriaFragment);
        }
    }

    private void nascondiPdiFragment(FragmentTransaction fragmentTransaction) {
        if (pdiFragment != null && pdiFragment.isAdded()) {
            fragmentTransaction.hide(pdiFragment);
        }
    }

    private void nascondiPdiDettaglioFragment(FragmentTransaction fragmentTransaction) {
        if (pdiDettaglioFragment != null && pdiDettaglioFragment.isAdded()) {
            fragmentTransaction.hide(pdiDettaglioFragment);
        }
    }

    private void nascondiCategoriaFragmentEFigli(FragmentTransaction fragmentTransaction) {
        nascondiCategoriaFragment(fragmentTransaction);
        nascondiPdiFragment(fragmentTransaction);
        nascondiPdiDettaglioFragment(fragmentTransaction);
    }

    private void nascondiGoogleMapsFragment(FragmentTransaction fragmentTransaction) {
        if (googleMapsFragment != null && googleMapsFragment.isAdded()) {
            fragmentTransaction.hide(googleMapsFragment);
        }
    }

    private void nascondiOpenStreetMapFragment(FragmentTransaction fragmentTransaction) {
        if (openStreetMapFragment != null && openStreetMapFragment.isAdded()) {
            fragmentTransaction.hide(openStreetMapFragment);
        }
    }

    private void attivaTab(int tabId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (tabId) {
            case R.id.item_pdi: {
                nascondiGoogleMapsFragment(fragmentTransaction);
                nascondiOpenStreetMapFragment(fragmentTransaction);

                if (pdiDettaglioFragment != null && pdiDettaglioFragment.isAdded()) {
                    nascondiPdiFragment(fragmentTransaction);

                    fragmentTransaction.show(pdiDettaglioFragment);

                    impostaActionBar(true, pdiScelto.titolo);
                } else if (pdiFragment != null && pdiFragment.isAdded()) {
                    nascondiCategoriaFragment(fragmentTransaction);

                    fragmentTransaction.show(pdiFragment);

                    impostaActionBar(true, categoriaScelta.nome);
                } else {
                    if (categoriaFragment.isAdded()) {
                        fragmentTransaction.show(categoriaFragment);
                    } else {
                        fragmentTransaction.add(R.id.contentContainer, categoriaFragment, "categoriaFragment");
                    }

                    impostaActionBar(false, getResources().getString(R.string.Categorie));
                }
            }
            break;

            case R.id.item_google_maps: {
                nascondiCategoriaFragmentEFigli(fragmentTransaction);
                nascondiOpenStreetMapFragment(fragmentTransaction);

                if (googleMapsFragment.isAdded()) {
                    fragmentTransaction.show(googleMapsFragment);
                } else {
                    fragmentTransaction.add(R.id.contentContainer, googleMapsFragment, "googleMapsFragment");
                }

                impostaActionBar(false, "Google Maps");
            }
            break;

            case R.id.item_open_street_map: {
                nascondiCategoriaFragmentEFigli(fragmentTransaction);
                nascondiGoogleMapsFragment(fragmentTransaction);

                if (openStreetMapFragment.isAdded()) {
                    fragmentTransaction.show(openStreetMapFragment);
                } else {
                    fragmentTransaction.add(R.id.contentContainer, openStreetMapFragment, "openStreetMapFragment");
                }

                impostaActionBar(false, "OpenStreetMap");
            }
        }

        fragmentTransaction.commit();
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

        impostaActionBar(true, categoriaScelta.nome);
    }
    //endregion
}
