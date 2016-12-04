package dcsoft.somsipinzano;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class PdiFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private ArrayList<Pdi> pdi;

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

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (savedInstanceState == null) {
                pdi = mainActivity.gestoreDatabaseCondiviso.dammiPdiPerCategoria(mainActivity.categoriaScelta.getIdCategoria());
            } else {
                //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

                pdi = savedInstanceState.getParcelableArrayList("pdi"); // @SuppressWarnings("unchecked")
            }

            recyclerView.setAdapter(new PdiRecyclerViewAdapter(pdi, mainActivity));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

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
}
