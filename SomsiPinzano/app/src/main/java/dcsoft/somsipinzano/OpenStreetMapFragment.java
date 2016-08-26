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

interface OpenStreetMapFragmentEseguiAlOnCreateView {
    public void esegui(TextView tvOSM);
}

public class OpenStreetMapFragment extends Fragment {
    private static final String TAG = "OpenStreetMapFragment ";
    private MainActivity mainActivity;
    private View openStreetMapFragmentView;

    public OpenStreetMapFragmentEseguiAlOnCreateView eseguiAlOnCreateView;

    public OpenStreetMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@Nullable Context context) {
        super.onAttach(context);

        Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
            mainActivity.openStreetMapFragment = this;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DEBUGAPP", TAG + "onCreateView");

        openStreetMapFragmentView = inflater.inflate(R.layout.fragment_open_street_map, container, false);

        TextView tvOSM = (TextView) openStreetMapFragmentView.findViewById(R.id.tvOSM);

        if (eseguiAlOnCreateView != null) {
            eseguiAlOnCreateView.esegui(tvOSM);
        }

        return openStreetMapFragmentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("DEBUGAPP", TAG + "onCreateView");

        mainActivity = null;
    }
}