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

public class CategoriaFragment extends Fragment {
    private static final String TAG = "CategoriaFragment ";
    private MainActivity mainActivity;
    private ArrayList<Categoria> categorie;

    public CategoriaFragment() {
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

        View view = inflater.inflate(R.layout.fragment_categoria_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (savedInstanceState == null) {
                DatabaseAdapter databaseAdapter = DatabaseAdapter.dammiDbHelperCondiviso(mainActivity);
                databaseAdapter.apriConnesioneDatabase();
                categorie = databaseAdapter.dammiCategorie();
                databaseAdapter.chiudiConnessioneDatabase();
            } else {
                //Log.d("DEBUGAPP", TAG + "onCreateView savedInstanceState != nul");

                categorie = (ArrayList<Categoria>) savedInstanceState.getSerializable("categorie"); // @SuppressWarnings("unchecked")
            }

            recyclerView.setAdapter(new CategoriaRecyclerViewAdapter(categorie, mainActivity));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + "onSaveInstanceState");

        outState.putSerializable("categorie", categorie);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
}
