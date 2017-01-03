package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PdiDettaglioFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private View pdiDettaglioFragmentView;
    private MainActivity mainActivity;

    private ScrollView svContenitore;

    private TextView tvIntestazioneDescrizione;
    private TextView tvDescrizione;

    private LinearLayout llIndirizzo;
    private Button btIndirizzo;

    private LinearLayout llTelefono;
    private Button btTelefono;

    private LinearLayout llFax;
    private Button btFax;

    private LinearLayout llCellulare;
    private Button btCellulare;

    private LinearLayout llEmail;
    private Button btEmail;

    private LinearLayout llLink;
    private TextView tvIntestazioneLink;
    private Button btLink;

    private LinearLayout llVideo;
    private Button btVideo;

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
        btIndirizzo               = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btIndirizzo);
        if (btIndirizzo != null) {
            btIndirizzo.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            copiaInClipboard(getResources().getString(R.string.indirizzo_copiato), (String) btIndirizzo.getText());
                        }
                    }
            );
        }

        llTelefono                = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llTelefono);
        btTelefono                = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btTelefono);
        if (btTelefono != null) {
            btTelefono.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("DEBUGAPP", TAG + " btTelefono");
                        }
                    }
            );
        }

        llFax                     = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llFax);
        btFax                     = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btFax);
        if (btFax != null) {
            btFax.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            copiaInClipboard(getResources().getString(R.string.fax_copiato), (String) btFax.getText());
                        }
                    }
            );
        }

        llCellulare               = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llCellulare);
        btCellulare               = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btCellulare);
        if (btCellulare != null) {
            btCellulare.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("DEBUGAPP", TAG + " btCellulare");
                        }
                    }
            );
        }

        llEmail                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llEmail);
        btEmail                   = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btEmail);
        if (btEmail != null) {
            btEmail.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("DEBUGAPP", TAG + " btEmail");
                        }
                    }
            );
        }

        llLink                    = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llLink);
        tvIntestazioneLink        = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink);
        btLink                    = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btLink);
        if (btLink != null) {
            btLink.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse((String) btLink.getText()));
                            startActivity(i);
                        }
                    }
            );
        }

        llVideo                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llVideo);
        btVideo                   = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btVideo);
        if (btVideo != null) {
            btVideo.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse((String) btVideo.getText()));
                            startActivity(i);
                        }
                    }
            );
        }

        bVediSuGM                 = (Button)       pdiDettaglioFragmentView.findViewById(R.id.bVediSuGM);
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

        bVediSuOSM                = (Button)       pdiDettaglioFragmentView.findViewById(R.id.bVediSuOSM);
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
        btIndirizzo.setText(indirizzo);

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
            btTelefono.setText(mainActivity.pdiScelto.getTelefono());
        } else {
            llTelefono.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getFax() != null) {
            btFax.setText(mainActivity.pdiScelto.getFax());
        } else {
            llFax.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getCellulare() != null) {
            btCellulare.setText(mainActivity.pdiScelto.getCellulare());
        } else {
            llCellulare.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getEmail() != null) {
            btEmail.setText(mainActivity.pdiScelto.getEmail());
        } else {
            llEmail.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkGenerico() != null) {
            btLink.setText(mainActivity.pdiScelto.getLinkGenerico());
        } else {
            llLink.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkVideo() != null) {
            btVideo.setText(mainActivity.pdiScelto.getLinkVideo());
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

    private void copiaInClipboard(String etichetta, String testo) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(testo);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(etichetta, testo);
            clipboard.setPrimaryClip(clip);
        }

        Toast.makeText(mainActivity, etichetta, Toast.LENGTH_SHORT).show();
    }
}
