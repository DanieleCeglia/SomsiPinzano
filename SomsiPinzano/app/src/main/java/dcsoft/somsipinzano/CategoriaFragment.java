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
import java.util.ArrayList;

public class CategoriaFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private MainActivity mainActivity;
    private ArrayList<Categoria> categorie;

    public CategoriaFragment() {
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

        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d("DEBUGAPP", TAG + " onCreateView");

        View view = inflater.inflate(R.layout.fragment_categoria_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            if (savedInstanceState == null) {
                if (FirebaseHelper.dammiFirebaseHelperCondiviso().scaricamentoDatabaseRiuscitoConSuccesso()) {
                    categorie = FirebaseHelper.dammiFirebaseHelperCondiviso().dammiCategorie();
                } else {
                    categorie = mainActivity.gestoreDatabaseCondiviso.dammiCategorie();
                }
            } else {
                //Log.d("DEBUGAPP", TAG + " onCreateView savedInstanceState != null");

                categorie = savedInstanceState.getParcelableArrayList("categorie"); // @SuppressWarnings("unchecked")
            }

            recyclerView.setAdapter(new CategoriaRecyclerViewAdapter(categorie, mainActivity));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.d("DEBUGAPP", TAG + " onSaveInstanceState");

        outState.putParcelableArrayList("categorie", categorie);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //Log.d("DEBUGAPP", TAG + " onDetach");

        mainActivity = null;
    }
}
