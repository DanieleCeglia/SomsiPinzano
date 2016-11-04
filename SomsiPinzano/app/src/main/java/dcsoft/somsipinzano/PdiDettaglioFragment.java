package dcsoft.somsipinzano;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class PdiDettaglioFragment extends Fragment {
    private static final String TAG = "PdiDettaglioFragment ";
    private View pdiDettaglioFragmentView;
    private MainActivity mainActivity;

    private TextView tvDescrizione;
    private ImageView ivImmagine;

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

        tvDescrizione = (TextView)  pdiDettaglioFragmentView.findViewById(R.id.tvDescrizione);
        ivImmagine    = (ImageView) pdiDettaglioFragmentView.findViewById(R.id.ivImmagine);

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
        }

        tvDescrizione.setText(mainActivity.pdiScelto.descrizione);

        String nomeFileSenzaEstensione = mainActivity.pdiScelto.fileImmagine.substring(0, mainActivity.pdiScelto.fileImmagine.lastIndexOf('.'));
        String packageName = mainActivity.getPackageName();
        ivImmagine.setImageResource(getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
}
