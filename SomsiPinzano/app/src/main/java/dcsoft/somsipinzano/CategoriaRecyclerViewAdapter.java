package dcsoft.somsipinzano;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class CategoriaRecyclerViewAdapter extends RecyclerView.Adapter<CategoriaRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CategoriaRecyclerViewAdapter ";
    private List <Categoria> categorie;
    private MainActivity mainActivity;

    CategoriaRecyclerViewAdapter(List<Categoria> categorie, MainActivity mainActivity) {
        this.categorie = categorie;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categoria, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvTitoloCategoria.setText(categorie.get(position).nome);
        holder.categoria = categorie.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mainActivity) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mainActivity.categoriaScelta(holder.categoria);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView ivImmagineCategoria;
        final TextView tvTitoloCategoria;
        Categoria categoria;

        ViewHolder(View view) {
            super(view);

            mView = view;
            ivImmagineCategoria = (ImageView) view.findViewById(R.id.ivImmagineCategoria);
            tvTitoloCategoria = (TextView) view.findViewById(R.id.tvTitoloCategoria);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitoloCategoria.getText() + "'";
        }
    }
}
