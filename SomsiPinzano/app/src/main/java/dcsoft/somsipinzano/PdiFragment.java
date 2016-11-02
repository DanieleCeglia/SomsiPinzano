package dcsoft.somsipinzano;

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
    private static final String TAG = "PdiFragment ";
    private MainActivity mainActivity;
    private ArrayList<Pdi> pdi;

    public PdiFragment() {
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

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + "onCreateView");

        View view = inflater.inflate(R.layout.fragment_pdi_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (savedInstanceState == null) {
                DatabaseAdapter databaseAdapter = DatabaseAdapter.dammiDbHelperCondiviso(mainActivity);
                databaseAdapter.apriConnesioneDatabase();
                pdi = databaseAdapter.dammiPdiPerCategoria(mainActivity.categoriaScelta.idCategoria);
                databaseAdapter.chiudiConnessioneDatabase();
            } else {
                //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != null");

                pdi = (ArrayList<Pdi>) savedInstanceState.getSerializable("pdi"); // @SuppressWarnings("unchecked")
            }

            recyclerView.setAdapter(new PdiRecyclerViewAdapter(pdi, mainActivity));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        outState.putSerializable("pdi", pdi);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Log.d("DEBUGAPP", TAG + "onResume");

        if (mainActivity.bottomBar.getCurrentTabId() == R.id.item_pdi) {
            mainActivity.impostaActionBar(true, mainActivity.categoriaScelta.nome);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
}
