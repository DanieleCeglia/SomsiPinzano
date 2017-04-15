package dcsoft.somsipinzano;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class PdiDettaglioFragment extends Fragment {
    private String numeroDaChiamare = "0";

    private final String TAG = getClass().getSimpleName();
    private View pdiDettaglioFragmentView;
    private MainActivity mainActivity;

    private ScrollView svContenitore;

    private TextView tvIntestazioneDescrizione;
    private DocumentView dvDescrizione;

    private LinearLayout llGalleria;
    private ImageView ivGalleria;
    private TextView tvDescrizioneImmagine;

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
    private int indiceImmagine;
    private boolean galleriaAperta;

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

        if (savedInstanceState == null) {
            immaginiPdi = mainActivity.gestoreDatabaseCondiviso.dammiImmaginiPdiPerPdi(mainActivity.pdiScelto.getIdPdi());
            indiceImmagine = 0;
            galleriaAperta = false;
        } else {
            //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            immaginiPdi = savedInstanceState.getParcelableArrayList("immaginiPdi"); // @SuppressWarnings("unchecked")
            indiceImmagine = savedInstanceState.getInt("indiceImmagine");
            galleriaAperta = savedInstanceState.getBoolean("galleriaAperta");
        }

        pdiDettaglioFragmentView = inflater.inflate(R.layout.fragment_pdi_dettaglio, container, false);

        svContenitore             = (ScrollView)     pdiDettaglioFragmentView.findViewById(R.id.svContenitore);

        tvIntestazioneDescrizione = (TextView)       pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneDescrizione);
        dvDescrizione             = (DocumentView)   pdiDettaglioFragmentView.findViewById(R.id.dvDescrizione);

        llGalleria                = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llGalleria);
        ivGalleria                = (ImageView)      pdiDettaglioFragmentView.findViewById(R.id.ivGalleria);
        tvDescrizioneImmagine     = (TextView)       pdiDettaglioFragmentView.findViewById(R.id.tvDescrizioneImmagine);
        if (ivGalleria != null) {
            ivGalleria.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mainActivity, Galleria.class);
                            intent.putParcelableArrayListExtra("immaginiPdi", immaginiPdi);
                            intent.putExtra("indiceImmagine", indiceImmagine);
                            startActivityForResult(intent, 1);

                            galleriaAperta = true;
                        }
                    }
            );
        }

        llIndirizzo               = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llIndirizzo);
        btIndirizzo               = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btIndirizzo);
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

        llTelefono                = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llTelefono);
        btTelefono                = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btTelefono);
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

        llFax                     = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llFax);
        btFax                     = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btFax);
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

        llCellulare               = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llCellulare);
        btCellulare               = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btCellulare);
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

        llEmail                   = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llEmail);
        btEmail                   = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btEmail);
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

        llLink1                   = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llLink1);
        tvIntestazioneLink1       = (TextView)       pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink1);
        btLink1                   = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btLink1);
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

        llLink2                   = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llLink2);
        tvIntestazioneLink2       = (TextView)       pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink2);
        btLink2                   = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btLink2);
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

        llLink3                   = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llLink3);
        tvIntestazioneLink3       = (TextView)       pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink3);
        btLink3                   = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btLink3);
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

        llLink4                   = (LinearLayout)   pdiDettaglioFragmentView.findViewById(R.id.llLink4);
        tvIntestazioneLink4       = (TextView)       pdiDettaglioFragmentView.findViewById(R.id.tvIntestazioneLink4);
        btLink4                   = (Button)         pdiDettaglioFragmentView.findViewById(R.id.btLink4);
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

        bVediSuGM                 = (Button)         pdiDettaglioFragmentView.findViewById(R.id.bVediSuGM);
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

        bVediSuOSM                = (Button)         pdiDettaglioFragmentView.findViewById(R.id.bVediSuOSM);
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

        if (immaginiPdi.size() > 0) {
            impostaImmagine();
        } else {
            llGalleria.setVisibility(View.GONE);
        }

        if (mainActivity.pdiScelto.getVia() != null) {
            String indirizzo = mainActivity.pdiScelto.getCitta() + ", " + mainActivity.pdiScelto.getVia();

            if (mainActivity.pdiScelto.getNumeroCivico() != 0) {
                indirizzo = indirizzo + ", " + mainActivity.pdiScelto.getNumeroCivico();
            }

            if (mainActivity.pdiScelto.getInterno() != null) {
                indirizzo = indirizzo + "/" + mainActivity.pdiScelto.getInterno();
            }

            indirizzo = indirizzo + ", " + mainActivity.pdiScelto.getCap();
            btIndirizzo.setText(indirizzo);
        } else {
            llIndirizzo.setVisibility(View.GONE);
        }

        switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
            case "italiano": {
                if (mainActivity.pdiScelto.getDescrizioneItaliano() != null) {
                    dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneItaliano());

                    final Handler handler = new Handler(); // serve per sistemare la rotazione di dvDescrizione che in landscape non prende tutta l'area orizzontale
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneItaliano());
                        }
                    }, 50);
                } else {
                    tvIntestazioneDescrizione.setVisibility(View.GONE);
                    dvDescrizione.setVisibility(View.GONE);
                }

                if (mainActivity.pdiScelto.getTitoloLink1GenericoItaliano() != null) {
                    tvIntestazioneLink1.setText(mainActivity.pdiScelto.getTitoloLink1GenericoItaliano() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink2GenericoItaliano() != null) {
                    tvIntestazioneLink2.setText(mainActivity.pdiScelto.getTitoloLink2GenericoItaliano() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink3GenericoItaliano() != null) {
                    tvIntestazioneLink3.setText(mainActivity.pdiScelto.getTitoloLink3GenericoItaliano() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink4GenericoItaliano() != null) {
                    tvIntestazioneLink4.setText(mainActivity.pdiScelto.getTitoloLink4GenericoItaliano() + ":");
                }
            }
            break;

            case "Deutsch": {
                if (mainActivity.pdiScelto.getDescrizioneTedesco() != null) {
                    dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneTedesco());

                    final Handler handler = new Handler(); // serve per sistemare la rotazione di dvDescrizione che in landscape non prende tutta l'area orizzontale
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneTedesco());
                        }
                    }, 50);
                } else {
                    tvIntestazioneDescrizione.setVisibility(View.GONE);
                    dvDescrizione.setVisibility(View.GONE);
                }

                if (mainActivity.pdiScelto.getTitoloLink1GenericoTedesco() != null) {
                    tvIntestazioneLink1.setText(mainActivity.pdiScelto.getTitoloLink1GenericoTedesco() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink2GenericoTedesco() != null) {
                    tvIntestazioneLink2.setText(mainActivity.pdiScelto.getTitoloLink2GenericoTedesco() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink3GenericoTedesco() != null) {
                    tvIntestazioneLink3.setText(mainActivity.pdiScelto.getTitoloLink3GenericoTedesco() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink4GenericoTedesco() != null) {
                    tvIntestazioneLink4.setText(mainActivity.pdiScelto.getTitoloLink4GenericoTedesco() + ":");
                }
            }
            break;

            case "français": {
                if (mainActivity.pdiScelto.getDescrizioneFrancese() != null) {
                    dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneFrancese());

                    final Handler handler = new Handler(); // serve per sistemare la rotazione di dvDescrizione che in landscape non prende tutta l'area orizzontale
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneFrancese());
                        }
                    }, 50);
                } else {
                    tvIntestazioneDescrizione.setVisibility(View.GONE);
                    dvDescrizione.setVisibility(View.GONE);
                }

                if (mainActivity.pdiScelto.getTitoloLink1GenericoFrancese() != null) {
                    tvIntestazioneLink1.setText(mainActivity.pdiScelto.getTitoloLink1GenericoFrancese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink2GenericoFrancese() != null) {
                    tvIntestazioneLink2.setText(mainActivity.pdiScelto.getTitoloLink2GenericoFrancese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink3GenericoFrancese() != null) {
                    tvIntestazioneLink3.setText(mainActivity.pdiScelto.getTitoloLink3GenericoFrancese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink4GenericoFrancese() != null) {
                    tvIntestazioneLink4.setText(mainActivity.pdiScelto.getTitoloLink4GenericoFrancese() + ":");
                }
            }
            break;

            default: {
                if (mainActivity.pdiScelto.getDescrizioneInglese() != null) {
                    dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneInglese());

                    final Handler handler = new Handler(); // serve per sistemare la rotazione di dvDescrizione che in landscape non prende tutta l'area orizzontale
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dvDescrizione.setText(mainActivity.pdiScelto.getDescrizioneInglese());
                        }
                    }, 50);
                } else {
                    tvIntestazioneDescrizione.setVisibility(View.GONE);
                    dvDescrizione.setVisibility(View.GONE);
                }

                if (mainActivity.pdiScelto.getTitoloLink1GenericoInglese() != null) {
                    tvIntestazioneLink1.setText(mainActivity.pdiScelto.getTitoloLink1GenericoInglese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink2GenericoInglese() != null) {
                    tvIntestazioneLink2.setText(mainActivity.pdiScelto.getTitoloLink2GenericoInglese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink3GenericoInglese() != null) {
                    tvIntestazioneLink3.setText(mainActivity.pdiScelto.getTitoloLink3GenericoInglese() + ":");
                }
                if (mainActivity.pdiScelto.getTitoloLink4GenericoInglese() != null) {
                    tvIntestazioneLink4.setText(mainActivity.pdiScelto.getTitoloLink4GenericoInglese() + ":");
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

        return pdiDettaglioFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        outState.putParcelableArrayList("immaginiPdi", immaginiPdi);
        outState.putInt("indiceImmagine", indiceImmagine);
        outState.putBoolean("galleriaAperta", galleriaAperta);
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

                case "Deutsch": {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloTedesco());
                }
                break;

                case "français": {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloFrancese());
                }
                break;

                default: {
                    mainActivity.impostaActionBar(true, mainActivity.pdiScelto.getTitoloInglese());
                }
            }
        }

        if (galleriaAperta) {
            SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("Galleria", 0);
            String stringaIndiceImmagine = sharedPreferences.getString("indiceImmagine", "0");

            indiceImmagine = Integer.parseInt(stringaIndiceImmagine);
            impostaImmagine();
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
            alertDialogBuilder.setCancelable(true);

            alertDialogBuilder.setMessage(getResources().getString(R.string.chiamare_il_numero) + " " + numeroDaChiamare + "?");

            alertDialogBuilder.setNegativeButton(
                    getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            alertDialogBuilder.setPositiveButton(
                    getResources().getString(R.string.chiama),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            Intent i = new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:" + numeroDaChiamare));
                            startActivity(i);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.CALL_PHONE}, mainActivity.MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    private void copiaInClipboard(String etichetta, String testo) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mainActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(etichetta, testo);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(mainActivity, etichetta, Toast.LENGTH_SHORT).show();
    }

    private void impostaImmagine() {
        ImmaginePdi immaginePdi = immaginiPdi.get(indiceImmagine);

        Glide.with(this).load(immaginePdi.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (e instanceof UnknownHostException) {
                            tvDescrizioneImmagine.setVisibility(View.GONE);
                        }

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        tvDescrizioneImmagine.setText("Immagine " + (indiceImmagine + 1) + " di " + immaginiPdi.size());
                        tvDescrizioneImmagine.setVisibility(View.VISIBLE);

                        return false;
                    }
                }).into(ivGalleria);
    }
}
