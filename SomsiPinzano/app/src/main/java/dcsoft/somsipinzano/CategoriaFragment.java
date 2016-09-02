package dcsoft.somsipinzano;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoriaFragment extends Fragment {
    private static final String TAG = "CategoriaFragment ";
    private MainActivity mainActivity;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("DEBUGAPP", TAG + "onAttach");

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DEBUGAPP", TAG + "onCreateView");

        View view = inflater.inflate(R.layout.fragment_categoria_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new CategoriaRecyclerViewAdapter(new String[]{"Torino", "Roma", "Milano", "Napoli", "Firenze"}, mainActivity));
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mainActivity.setActionBarTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d("DEBUGAPP", TAG + "onDetach");

        mainActivity = null;
    }
}
