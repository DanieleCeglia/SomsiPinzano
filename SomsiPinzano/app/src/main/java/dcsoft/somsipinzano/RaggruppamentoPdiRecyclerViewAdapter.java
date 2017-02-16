package dcsoft.somsipinzano;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

class RaggruppamentoPdiRecyclerViewAdapter extends RecyclerView.Adapter<RaggruppamentoPdiRecyclerViewAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<RaggruppamentoPdi> raggruppamentiPdi;
    private MainActivity mainActivity;

    RaggruppamentoPdiRecyclerViewAdapter(ArrayList<RaggruppamentoPdi> raggruppamentiPdi, MainActivity mainActivity) {
        this.raggruppamentiPdi = raggruppamentiPdi;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pdi, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
            case "italiano": {
                holder.tvRaggruppamentoPdi.setText(raggruppamentiPdi.get(position).getNomeRaggruppamentoItaliano());
            }
            break;

            case "Deutsch": {
                holder.tvRaggruppamentoPdi.setText(raggruppamentiPdi.get(position).getNomeRaggruppamentoTedesco());
            }
            break;

            case "français": {
                holder.tvRaggruppamentoPdi.setText(raggruppamentiPdi.get(position).getNomeRaggruppamentoFrancese());
            }
            break;

            default: {
                holder.tvRaggruppamentoPdi.setText(raggruppamentiPdi.get(position).getNomeRaggruppamentoInglese());
            }
        }
        holder.raggruppamentoPdi = raggruppamentiPdi.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mainActivity) {
                    mainActivity.raggruppamentoPdiScelto(holder.raggruppamentoPdi);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return raggruppamentiPdi.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView tvRaggruppamentoPdi;
        RaggruppamentoPdi raggruppamentoPdi;

        ViewHolder(View view) {
            super(view);

            mView = view;
            tvRaggruppamentoPdi = (TextView) view.findViewById(R.id.tvTitoloPdi);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvRaggruppamentoPdi.getText() + "'";
        }
    }
}
