package dcsoft.somsipinzano;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GoogleMapsFragment extends Fragment {
    private static final String TAG = "GoogleMapsFragment ";
    private MainActivity mainActivity;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@Nullable Context context) {
        super.onAttach(context);

        Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
            //mainActivity.googleMapsFragment = this;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DEBUGAPP", TAG + "onCreateView");

        return inflater.inflate(R.layout.fragment_google_maps, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("DEBUGAPP", TAG + "onCreateView");

        mainActivity = null;
    }
}
