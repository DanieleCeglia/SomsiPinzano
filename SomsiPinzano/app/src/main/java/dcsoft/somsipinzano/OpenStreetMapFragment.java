package dcsoft.somsipinzano;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OpenStreetMapFragment extends Fragment {
    private static final String TAG = "OpenStreetMapFragment ";
    private MainActivity mainActivity;

    public OpenStreetMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@Nullable Context context) {
        super.onAttach(context);

        Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof Activity){
            mainActivity = (MainActivity) context;
            mainActivity.openStreetMapFragment = this;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_open_street_map, container, false);
    }
}
