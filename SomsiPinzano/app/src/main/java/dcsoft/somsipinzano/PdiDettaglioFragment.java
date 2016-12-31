package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PdiDettaglioFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private View pdiDettaglioFragmentView;
    private MainActivity mainActivity;

    private ScrollView svContenitore;

    private TextView tvIntestazioneDescrizione;
    private TextView tvDescrizione;

    private LinearLayout llIndirizzo;
    private TextView tvIndirizzo;

    private LinearLayout llTelefono;
    private TextView tvTelefono;

    private LinearLayout llFax;
    private TextView tvFax;

    private LinearLayout llCellulare;
    private TextView tvCellulare;

    private LinearLayout llEmail;
    private TextView tvEmail;

    private LinearLayout llLink;
    private TextView tvIntestazioneLink;
    private TextView tvLink;

    private LinearLayout llVideo;
    private TextView tvVideo;

    private Button bVediSuGM;
    private Button bVediSuOSM;

    public PdiDettaglioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) { // per API < 23
        super.onAttach(activity);

        //Log.d("DEBUGAPP", TAG + " onAttach");

        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Log.d("DEBUGAPP", TAG + " onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + " onCreateView");

        pdiDettaglioFragmentView = inflater.inflate(R.layout.fragment_pdi_dettaglio, container, false);

        svContenitore             = (ScrollView)   pdiDettaglioFragmentView.findViewById(R.id.svContenitore);

        tvIntestazioneDescrizione = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneDescrizione);
        tvDescrizione             = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvDescrizione);

        llIndirizzo               = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llIndirizzo);
        tvIndirizzo               = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIndirizzo);

        llTelefono                = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llTelefono);
        tvTelefono                = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvTelefono);

        llFax                     = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llFax);
        tvFax                     = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvFax);

        llCellulare               = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llCellulare);
        tvCellulare               = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvCellulare);

        llEmail                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llEmail);
        tvEmail                   = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvEmail);

        llLink                    = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llLink);
        tvIntestazioneLink        = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink);
        tvLink                    = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvLink);

        llVideo                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llVideo);
        tvVideo                   = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvVideo);

        bVediSuGM                 = (Button)       pdiDettaglioFragmentView.findViewById(R.id.bVediSuGM);
        bVediSuOSM                = (Button)       pdiDettaglioFragmentView.findViewById(R.id.bVediSuOSM);

        if (bVediSuGM != null) {
            bVediSuGM.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainActivity.vediPdiSceltoSuGM();
                        }
                    }
            );
        }

        if (bVediSuOSM != null) {
            bVediSuOSM.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainActivity.vediPdiSceltoSuOSM();
                        }
                    }
            );
        }

        if (savedInstanceState != null) {
            //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            svContenitore.setY(savedInstanceState.getFloat("svContenitoreY"));
        }

        return pdiDettaglioFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        outState.putFloat("svContenitoreY", svContenitore.getY());
    }

    @Override
    public void onResume() {
        super.onResume();

        //Log.d("DEBUGAPP", TAG + " onResume");

        if (mainActivity.tabSelezionato == 0) {
            switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
                case "italiano": {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloItaliano());
                }
                break;

                default: {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloInglese());
                }
            }
        }

        String indirizzo = mainActivity.pdiScelto.getCitta() + ", " + mainActivity.pdiScelto.getVia();

        if (mainActivity.pdiScelto.getNumeroCivico() > 0) {
            indirizzo = indirizzo + ", " + mainActivity.pdiScelto.getNumeroCivico();
        }

        if (mainActivity.pdiScelto.getInterno() != null) {
            indirizzo = indirizzo + "/" + mainActivity.pdiScelto.getInterno();
        }

        indirizzo = indirizzo + ", " + mainActivity.pdiScelto.getCap();
        tvIndirizzo.setText(indirizzo);

        switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
            case "italiano": {
                if (mainActivity.pdiScelto.getDescrizioneItaliano() != null) {
                    tvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneItaliano());
                } else {
                    tvIntestazioneDescrizione.setVisibility(View.GONE);
                    tvDescrizione.setVisibility(View.GONE);
                }

                if (mainActivity.pdiScelto.getTitoloLinkGenericoItaliano() != null) {
                    tvIntestazioneLink.setText(mainActivity.pdiScelto.getTitoloLinkGenericoItaliano() + ":");
                }
            }
            break;

            default: {
                if (mainActivity.pdiScelto.getDescrizioneInglese() != null) {
                    tvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneInglese());
                } else {
                    tvIntestazioneDescrizione.setVisibility(View.GONE);
                    tvDescrizione.setVisibility(View.GONE);
                }

                if (mainActivity.pdiScelto.getTitoloLinkGenericoInglese() != null) {
                    tvIntestazioneLink.setText(mainActivity.pdiScelto.getTitoloLinkGenericoInglese() + ":");
                }
            }
        }

        if (mainActivity.pdiScelto.getTelefono() != null) {
            tvTelefono.setText(mainActivity.pdiScelto.getTelefono());
        } else {
            llTelefono.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getFax() != null) {
            tvFax.setText(mainActivity.pdiScelto.getFax());
        } else {
            llFax.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getCellulare() != null) {
            tvCellulare.setText(mainActivity.pdiScelto.getCellulare());
        } else {
            llCellulare.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getEmail() != null) {
            tvEmail.setText(mainActivity.pdiScelto.getEmail());
        } else {
            llEmail.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkGenerico() != null) {
            tvLink.setText(mainActivity.pdiScelto.getLinkGenerico());
        } else {
            llLink.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkVideo() != null) {
            tvVideo.setText(mainActivity.pdiScelto.getLinkVideo());
        } else {
            llVideo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + " onDetach");

        mainActivity = null;
    }
}
