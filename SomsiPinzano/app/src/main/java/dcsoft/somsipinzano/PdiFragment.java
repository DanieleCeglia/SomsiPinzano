package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PdiFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private ArrayList<RaggruppamentoPdi> raggruppamentiPdi;
    private ArrayList<Pdi> pdi;
    private RecyclerView recyclerView;

    public PdiFragment() {
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

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + " onCreateView");

        View view = inflater.inflate(R.layout.fragment_pdi_list, container, false);

        ImageView ivCopertinaCategoria = (ImageView) view.findViewById(R.id.ivCopertinaCategoria);

        if (ivCopertinaCategoria != null && mainActivity.categoriaScelta.getFileImmagineCopertina() != null && mainActivity.categoriaScelta.getFileImmagineCopertina().length() > 0) {
            String nomeFileSenzaEstensione = mainActivity.categoriaScelta.getFileImmagineCopertina().substring(0, mainActivity.categoriaScelta.getFileImmagineCopertina().lastIndexOf('.'));
            String packageName = mainActivity.getPackageName();
            ivCopertinaCategoria.setImageResource(mainActivity.getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));
        }

        ImageView ivPinPdi = (ImageView) view.findViewById(R.id.ivPinPdi);

        if (ivPinPdi!= null && mainActivity.categoriaScelta.getFilePin() != null) {
            String nomeFileSenzaEstensione = mainActivity.categoriaScelta.getFilePin().substring(0, mainActivity.categoriaScelta.getFilePin().lastIndexOf('.'));
            String packageName = mainActivity.getPackageName();
            ivPinPdi.setImageResource(mainActivity.getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));
        }

        TextView tvDescrizioneCategoriaPdi = (TextView) view.findViewById(R.id.tvDescrizioneCategoriaPdi);

        if (tvDescrizioneCategoriaPdi != null) {
            switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
                case "italiano": {
                    tvDescrizioneCategoriaPdi.setText(mainActivity.categoriaScelta.getDescrizioneItaliano());
                }
                break;

                case "Deutsch": {
                    tvDescrizioneCategoriaPdi.setText(mainActivity.categoriaScelta.getDescrizioneTedesco());
                }
                break;

                case "français": {
                    tvDescrizioneCategoriaPdi.setText(mainActivity.categoriaScelta.getDescrizioneFrancese());
                }
                break;

                default: {
                    tvDescrizioneCategoriaPdi.setText(mainActivity.categoriaScelta.getDescrizioneInglese());
                }
            }
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.listPdi);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if (savedInstanceState == null) {
            if (mainActivity.categoriaScelta.getIdCategoria() != null) {
                if (FirebaseHelper.dammiFirebaseHelperCondiviso().scaricamentoDatabaseRiuscitoConSuccesso()) {
                    raggruppamentiPdi = FirebaseHelper.dammiFirebaseHelperCondiviso().dammiRaggruppamentiPdiPerCategoria(mainActivity.categoriaScelta.getIdCategoria());
                } else {
                    raggruppamentiPdi = mainActivity.gestoreDatabaseCondiviso.dammiRaggruppamentiPdiPerCategoria(mainActivity.categoriaScelta.getIdCategoria());
                }
            }
        } else {
            //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

            raggruppamentiPdi = savedInstanceState.getParcelableArrayList("raggruppamentiPdi");
            pdi = savedInstanceState.getParcelableArrayList("pdi");
        }

        ricarica();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        outState.putParcelableArrayList("raggruppamentiPdi", raggruppamentiPdi);
        outState.putParcelableArrayList("pdi", pdi);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Log.d("DEBUGAPP", TAG + " onResume");

        if (mainActivity.tabSelezionato == 0) {
            switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
                case "italiano": {
                    mainActivity.impostaActionBar(true, mainActivity.categoriaScelta.getNomeItaliano());
                }
                break;

                case "Deutsch": {
                    mainActivity.impostaActionBar(true, mainActivity.categoriaScelta.getNomeTedesco());
                }
                break;

                case "français": {
                    mainActivity.impostaActionBar(true, mainActivity.categoriaScelta.getNomeFrancese());
                }
                break;

                default: {
                    mainActivity.impostaActionBar(true, mainActivity.categoriaScelta.getNomeInglese());
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + " onDetach");

        mainActivity = null;
    }

    public void ricarica() {
        if (raggruppamentiPdi != null) {
            if (raggruppamentiPdi.size() > 0 && mainActivity.raggruppamentoPdiScelto == null) {
                recyclerView.setAdapter(new RaggruppamentoPdiRecyclerViewAdapter(raggruppamentiPdi, mainActivity));
            } else {
                if (raggruppamentiPdi.size() == 0) {
                    if (mainActivity.categoriaScelta.getIdCategoria() != null) {
                        if (FirebaseHelper.dammiFirebaseHelperCondiviso().scaricamentoDatabaseRiuscitoConSuccesso()) {
                            pdi = FirebaseHelper.dammiFirebaseHelperCondiviso().dammiPdiPerCategoria(mainActivity.categoriaScelta.getIdCategoria());
                        } else {
                            pdi = mainActivity.gestoreDatabaseCondiviso.dammiPdiPerCategoria(mainActivity.categoriaScelta.getIdCategoria());
                        }
                    }
                } else {
                    if (mainActivity.raggruppamentoPdiScelto.getIdRaggruppamentoPdi() != null) {
                        if (FirebaseHelper.dammiFirebaseHelperCondiviso().scaricamentoDatabaseRiuscitoConSuccesso()) {
                            pdi = FirebaseHelper.dammiFirebaseHelperCondiviso().dammiPdiPerRaggruppamento(mainActivity.raggruppamentoPdiScelto.getIdRaggruppamentoPdi());
                        } else {
                            pdi = mainActivity.gestoreDatabaseCondiviso.dammiPdiPerRaggruppamento(mainActivity.raggruppamentoPdiScelto.getIdRaggruppamentoPdi());
                        }
                    }
                }

                recyclerView.setAdapter(new PdiRecyclerViewAdapter(pdi, mainActivity));
            }
        }
    }
}
