package dcsoft.somsipinzano;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PdiDettaglioFragment extends Fragment {
    private static final String TAG = "PdiDettaglioFragment ";
    private View pdiDettaglioFragmentView;
    private MainActivity mainActivity;

    private TextView testDettaglioPdi;

    public PdiDettaglioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        pdiDettaglioFragmentView = inflater.inflate(R.layout.fragment_pdi_dettaglio, container, false);

        testDettaglioPdi = (TextView) pdiDettaglioFragmentView.findViewById(R.id.testDettaglioPdi);

        if (savedInstanceState == null) {
        } else {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != null");
        }

        return pdiDettaglioFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");
    }

    @Override
    public void onResume() {
        super.onResume();

        //Log.d("DEBUGAPP", TAG + "onResume");

        if (mainActivity.bottomBar.getCurrentTabId() == R.id.item_pdi) {
            mainActivity.impostaActionBar(true, mainActivity.pdiScelto.titolo);
            testDettaglioPdi.setText(mainActivity.pdiScelto.descrizione);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
}
