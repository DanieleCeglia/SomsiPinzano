package dcsoft.somsipinzano;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    public Categoria categoriaScelta;

    private static final String TAG = "MainActivity ";
    private FragmentManager fragmentManager;
    private CategoriaFragment categoriaFragment;
    private PdiFragment pdiFragment;
    private GoogleMapsFragment googleMapsFragment;
    private OpenStreetMapFragment openStreetMapFragment;
    private BottomBar bottomBar;
    private ActionBar actionBar;

    //region Metodi override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d("DEBUGAPP", TAG + "onCreate");

        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        actionBar = getSupportActionBar();

        if (savedInstanceState == null) {
            categoriaScelta = null;

            categoriaFragment     = new CategoriaFragment();
            pdiFragment           = null;
            googleMapsFragment    = new GoogleMapsFragment();
            openStreetMapFragment = new OpenStreetMapFragment();

            openStreetMapFragment.eseguiAlOnHiddenChanged = new OpenStreetMapFragmentEseguiAlOnHiddenChanged() {
                @Override
                public void esegui(boolean hidden, TextView tvOSM) {
                    //Log.d("DEBUGAPP", TAG + "OpenStreetMapFragmentEseguiAlOnStart");

                    if (!hidden) {
                        tvOSM.append(" asd");
                    }
                }
            };

            if (bottomBar != null) {
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

            impostaActionBar(false, getResources().getString(R.string.Categorie));
        } else {
            Log.d("DEBUGAPP", TAG + "onCreate 2!!!!");

            categoriaScelta = (Categoria) savedInstanceState.getSerializable("categorie");

            categoriaFragment     = (CategoriaFragment)     fragmentManager.findFragmentByTag("categoriaFragment");
            pdiFragment           = (PdiFragment)           fragmentManager.findFragmentByTag("pdiFragment");
            googleMapsFragment    = (GoogleMapsFragment)    fragmentManager.findFragmentByTag("googleMapsFragment");
            openStreetMapFragment = (OpenStreetMapFragment) fragmentManager.findFragmentByTag("openStreetMapFragment");
        }

        Log.d("DEBUGAPP", TAG + "bottomBar: " + bottomBar.toString());
        Log.d("DEBUGAPP", TAG + "actionBar: " + actionBar.toString());
        if (categoriaScelta == null) {
            Log.d("DEBUGAPP", TAG + "categoriaScelta: null");
        } else {
            Log.d("DEBUGAPP", TAG + "categoriaScelta: " + categoriaScelta.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        savedInstanceState.putSerializable("categoriaScelta", categoriaScelta);

        //savedInstanceState.putBoolean("MyBoolean", true);
        //savedInstanceState.putDouble("myDouble", 1.9);
        //savedInstanceState.putInt("MyInt", 1);
        //savedInstanceState.putString("MyString", "Welcome back to Android");
    }

    @Override
    public void onBackPressed() {
        if (pdiFragment!= null && pdiFragment.isVisible()) {
            rimuoviPdiFragment();
        } else {
            this.finishAffinity();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                rimuoviPdiFragment();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
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
        fragmentTransaction.add(R.id.contentContainer, pdiFragment);
        fragmentTransaction.commit();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void pdiScelto(String pdi) {
        Log.d("DEBUGAPP", TAG + "pdiScelto: " + pdi);
    }
    //endregion

    //region Metodi privati
    private void nascondiCategoriaFragmentEFigli(FragmentTransaction fragmentTransaction) {
        if (categoriaFragment.isAdded()) {
            fragmentTransaction.hide(categoriaFragment);

            if (pdiFragment!= null && pdiFragment.isAdded()) {
                fragmentTransaction.hide(pdiFragment);
            }
        }
    }

    private void nascondiGoogleMapsFragment(FragmentTransaction fragmentTransaction) {
        if (googleMapsFragment.isAdded()) {
            fragmentTransaction.hide(googleMapsFragment);
        }
    }

    private void nascondiOpenStreetMapFragment(FragmentTransaction fragmentTransaction) {
        if (openStreetMapFragment.isAdded()) {
            fragmentTransaction.hide(openStreetMapFragment);
        }
    }

    private void attivaTab(int tabId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (tabId) {
            case R.id.item_pdi: {
                nascondiGoogleMapsFragment(fragmentTransaction);
                nascondiOpenStreetMapFragment(fragmentTransaction);

                if (pdiFragment!= null && pdiFragment.isAdded()) {
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
    //endregion
}
