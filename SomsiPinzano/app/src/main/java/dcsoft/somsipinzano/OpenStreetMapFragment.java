package dcsoft.somsipinzano;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

interface OpenStreetMapFragmentEseguiAlOnHiddenChanged {
    void esegui(boolean hidden, TextView tvOSM);
}

public class OpenStreetMapFragment extends Fragment {
    private static final String TAG = "OpenStreetMapFragment ";
    private MainActivity mainActivity;
    private View openStreetMapFragmentView;

    public OpenStreetMapFragmentEseguiAlOnHiddenChanged eseguiAlOnHiddenChanged;

    public OpenStreetMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@Nullable Context context) {
        super.onAttach(context);

        //Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        openStreetMapFragmentView = inflater.inflate(R.layout.fragment_open_street_map, container, false);

        if (savedInstanceState != null) {
            //Log.d("DEBUGAPP", TAG + "savedInstanceState!!!!!!!!!!!!!");
            
            TextView tvOSM = (TextView) openStreetMapFragmentView.findViewById(R.id.tvOSM);

            tvOSM.setText(savedInstanceState.getString("tvOSM_testo"));
        }

        return openStreetMapFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        TextView tvOSM = (TextView) openStreetMapFragmentView.findViewById(R.id.tvOSM);

        outState.putString("tvOSM_testo", tvOSM.getText().toString());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        //Log.d("DEBUGAPP", TAG + "onCreateView");

        if (eseguiAlOnHiddenChanged != null) {
            eseguiAlOnHiddenChanged.esegui(hidden, (TextView) openStreetMapFragmentView.findViewById(R.id.tvOSM));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onCreateView");

        mainActivity = null;
    }
}