package dcsoft.somsipinzano;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PdiDettaglioFragment extends Fragment {
    private static final String TAG = "PdiDettaglioFragment ";
    private View pdiDettaglioFragmentView;
    private MainActivity mainActivity;

    private ScrollView svContenitore;
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

        svContenitore = (ScrollView)  pdiDettaglioFragmentView.findViewById(R.id.svContenitore);
        tvDescrizione = (TextView)  pdiDettaglioFragmentView.findViewById(R.id.tvDescrizione);
        ivImmagine    = (ImageView) pdiDettaglioFragmentView.findViewById(R.id.ivImmagine);

        if (savedInstanceState == null) {
        } else {
            //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != null");

            svContenitore.setY(savedInstanceState.getFloat("svContenitoreY"));
        }

        return pdiDettaglioFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        outState.putFloat("svContenitoreY", svContenitore.getY());
    }

    @Override
    public void onResume() {
        super.onResume();

        //Log.d("DEBUGAPP", TAG + "onResume");

        if (mainActivity.bottomBar.getCurrentTabId() == R.id.item_pdi) {
            switch (mainActivity.databaseAdapter.getLingua()) {
                case "italiano": {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloItaliano());
                }
                break;

                default: {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloInglese());
                }
            }
        }

        switch (mainActivity.databaseAdapter.getLingua()) {
            case "italiano": {
                if (mainActivity.pdiScelto.getDescrizioneItaliano() != null) {
                    tvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneItaliano());
                } else {
                    tvDescrizione.setText("");
                }
            }
            break;

            default: {
                if (mainActivity.pdiScelto.getDescrizioneInglese() != null) {
                    tvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneInglese());
                } else {
                    tvDescrizione.setText("");
                }
            }
        }

        if (mainActivity.pdiScelto.getFileImmagine() != null) {
            String nomeFileSenzaEstensione = mainActivity.pdiScelto.getFileImmagine().substring(0, mainActivity.pdiScelto.getFileImmagine().lastIndexOf('.'));
            String packageName = mainActivity.getPackageName();
            ivImmagine.setImageResource(getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
}
