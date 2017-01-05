package dcsoft.somsipinzano;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PdiDettaglioFragment extends Fragment {
    private String numeroDaChiamare = "0";

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

    private LinearLayout llLink1;
    private TextView tvIntestazioneLink1;
    private Button btLink1;

    private LinearLayout llLink2;
    private TextView tvIntestazioneLink2;
    private Button btLink2;

    private LinearLayout llLink3;
    private TextView tvIntestazioneLink3;
    private Button btLink3;

    private LinearLayout llLink4;
    private TextView tvIntestazioneLink4;
    private Button btLink4;

    private Button bVediSuGM;
    private Button bVediSuOSM;

    private ArrayList<ImmaginePdi> immaginiPdi;

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
                            copiaInClipboard(getResources().getString(R.string.indirizzo_copiato_negli_appunti), (String) btIndirizzo.getText());
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
                            chiamaNumeroTelefonico((String) btTelefono.getText());
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
                            copiaInClipboard(getResources().getString(R.string.fax_copiato_negli_appunti), (String) btFax.getText());
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
                            chiamaNumeroTelefonico((String) btCellulare.getText());
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
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL, new String[]{(String) btEmail.getText()});
                            startActivity(Intent.createChooser(i, getString(R.string.invia_email_a) + btEmail.getText() + getString(R.string.usando)));
                        }
                    }
            );
        }

        llLink1                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llLink1);
        tvIntestazioneLink1       = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink1);
        btLink1                   = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btLink1);
        if (btLink1 != null) {
            btLink1.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse((String) btLink1.getText()));
                            startActivity(i);
                        }
                    }
            );
        }

        llLink2                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llLink2);
        tvIntestazioneLink2       = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink2);
        btLink2                   = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btLink2);
        if (btLink2 != null) {
            btLink2.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse((String) btLink2.getText()));
                            startActivity(i);
                        }
                    }
            );
        }

        llLink3                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llLink3);
        tvIntestazioneLink3       = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink3);
        btLink3                   = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btLink3);
        if (btLink3 != null) {
            btLink3.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse((String) btLink3.getText()));
                            startActivity(i);
                        }
                    }
            );
        }

        llLink4                   = (LinearLayout) pdiDettaglioFragmentView.findViewById(R.id.llLink4);
        tvIntestazioneLink4       = (TextView)     pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink4);
        btLink4                   = (Button)       pdiDettaglioFragmentView.findViewById(R.id.btLink4);
        if (btLink4 != null) {
            btLink4.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse((String) btLink4.getText()));
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

        if (savedInstanceState == null) {
            immaginiPdi = mainActivity.gestoreDatabaseCondiviso.dammiImmaginiPdiPerPdi(mainActivity.pdiScelto.getIdPdi());
        } else {
            //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            immaginiPdi = savedInstanceState.getParcelableArrayList("immaginiPdi"); // @SuppressWarnings("unchecked")
            svContenitore.setY(savedInstanceState.getFloat("svContenitoreY"));
        }

        return pdiDettaglioFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        outState.putParcelableArrayList("immaginiPdi", immaginiPdi);
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

                if (mainActivity.pdiScelto.getTitoloLinkGenerico1Italiano() != null) {
                    tvIntestazioneLink1.setText(mainActivity.pdiScelto.getTitoloLinkGenerico1Italiano() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLinkGenerico2Italiano() != null) {
                    tvIntestazioneLink2.setText(mainActivity.pdiScelto.getTitoloLinkGenerico2Italiano() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLinkGenerico3Italiano() != null) {
                    tvIntestazioneLink3.setText(mainActivity.pdiScelto.getTitoloLinkGenerico3Italiano() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLinkGenerico4Italiano() != null) {
                    tvIntestazioneLink4.setText(mainActivity.pdiScelto.getTitoloLinkGenerico4Italiano() + ":");
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

                if (mainActivity.pdiScelto.getTitoloLinkGenerico1Inglese() != null) {
                    tvIntestazioneLink1.setText(mainActivity.pdiScelto.getTitoloLinkGenerico1Inglese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLinkGenerico2Inglese() != null) {
                    tvIntestazioneLink2.setText(mainActivity.pdiScelto.getTitoloLinkGenerico2Inglese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLinkGenerico3Inglese() != null) {
                    tvIntestazioneLink3.setText(mainActivity.pdiScelto.getTitoloLinkGenerico3Inglese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLinkGenerico4Inglese() != null) {
                    tvIntestazioneLink4.setText(mainActivity.pdiScelto.getTitoloLinkGenerico4Inglese() + ":");
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

        if (mainActivity.pdiScelto.getLinkGenerico1() != null) {
            btLink1.setText(mainActivity.pdiScelto.getLinkGenerico1());
        } else {
            llLink1.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkGenerico2() != null) {
            btLink2.setText(mainActivity.pdiScelto.getLinkGenerico2());
        } else {
            llLink2.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkGenerico3() != null) {
            btLink3.setText(mainActivity.pdiScelto.getLinkGenerico3());
        } else {
            llLink3.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getLinkGenerico4() != null) {
            btLink4.setText(mainActivity.pdiScelto.getLinkGenerico4());
        } else {
            llLink4.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + " onDetach");

        mainActivity = null;
    }

    public void chiamaNumeroTelefonico(String numero) {
        if (numero != null) {
            numeroDaChiamare = numero;
        }

        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + numeroDaChiamare));
            startActivity(i);
        } else {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.CALL_PHONE}, mainActivity.MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
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
