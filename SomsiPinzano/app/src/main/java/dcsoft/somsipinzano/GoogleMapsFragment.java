package dcsoft.somsipinzano;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

interface GoogleMapsFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden);
}

public class GoogleMapsFragment extends Fragment {
    private static final String TAG = "GoogleMapsFragment ";
    private View googleMapsFragmentView;

    public GoogleMapsFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        googleMapsFragmentView = inflater.inflate(R.layout.fragment_google_maps, container, false);

        if (savedInstanceState != null) {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != nul");
        }

        return googleMapsFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");
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
