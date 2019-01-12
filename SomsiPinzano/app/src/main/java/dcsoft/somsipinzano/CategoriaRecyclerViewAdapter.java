package dcsoft.somsipinzano;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

class CategoriaRecyclerViewAdapter extends RecyclerView.Adapter<CategoriaRecyclerViewAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<Categoria> categorie;
    private MainActivity mainActivity;

    CategoriaRecyclerViewAdapter(ArrayList<Categoria> categorie, MainActivity mainActivity) {
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
        switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
            case "italiano": {
                holder.tvTitoloCategoria.setText(categorie.get(position).getNomeItaliano());
                holder.tvDescrizioneCategoria.setText(categorie.get(position).getDescrizioneItaliano());
            }
            break;

            case "Deutsch": {
                holder.tvTitoloCategoria.setText(categorie.get(position).getNomeTedesco());
                holder.tvDescrizioneCategoria.setText(categorie.get(position).getDescrizioneTedesco());
            }
            break;

            case "français": {
                holder.tvTitoloCategoria.setText(categorie.get(position).getNomeFrancese());
                holder.tvDescrizioneCategoria.setText(categorie.get(position).getDescrizioneFrancese());
            }
            break;

            default: {
                holder.tvTitoloCategoria.setText(categorie.get(position).getNomeInglese());
                holder.tvDescrizioneCategoria.setText(categorie.get(position).getDescrizioneInglese());
            }
        }

        if (categorie.get(position).getFileImmagine() != null && categorie.get(position).getFileImmagine().length() > 0) {
            String nomeFileSenzaEstensione = categorie.get(position).getFileImmagine().substring(0, categorie.get(position).getFileImmagine().lastIndexOf('.'));
            String packageName = mainActivity.getPackageName();
            holder.ivCategoria.setImageResource(mainActivity.getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));
        } else {
            holder.ivCategoria.setImageDrawable(null);
        }

        if (categorie.get(position).getFilePin() != null) {
            String nomeFileSenzaEstensione = categorie.get(position).getFilePin().substring(0, categorie.get(position).getFilePin().lastIndexOf('.'));
            String packageName = mainActivity.getPackageName();
            holder.ivPin.setImageResource(mainActivity.getResources().getIdentifier(nomeFileSenzaEstensione, "drawable", packageName));
        }

        holder.categoria = categorie.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mainActivity) {
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
        final ImageView ivCategoria;
        final ImageView ivPin;
        final TextView tvTitoloCategoria;
        final TextView tvDescrizioneCategoria;
        Categoria categoria;

        ViewHolder(View view) {
            super(view);

            mView = view;
            ivCategoria            = (ImageView) view.findViewById(R.id.ivCategoria);
            ivPin                  = (ImageView) view.findViewById(R.id.ivPin);
            tvTitoloCategoria      = (TextView)  view.findViewById(R.id.tvTitoloCategoria);
            tvDescrizioneCategoria = (TextView)  view.findViewById(R.id.tvDescrizioneCategoria);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitoloCategoria.getText() + "'";
        }
    }
}
