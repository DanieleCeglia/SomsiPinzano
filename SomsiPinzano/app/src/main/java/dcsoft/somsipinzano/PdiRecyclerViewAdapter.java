package dcsoft.somsipinzano;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PdiRecyclerViewAdapter extends RecyclerView.Adapter<PdiRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "PdiRecyclerViewAdapter ";
    private String[] pdi;
    private MainActivity mainActivity;

    public PdiRecyclerViewAdapter(String[] pdi, MainActivity mainActivity) {
        this.pdi = pdi;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pdi, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvTitoloPdi.setText(pdi[position]);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mainActivity) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mainActivity.pdiScelto(holder.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdi.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ivImmaginePdi;
        public final TextView tvTitoloPdi;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            ivImmaginePdi = (ImageView) view.findViewById(R.id.ivImmaginePdi);
            tvTitoloPdi = (TextView) view.findViewById(R.id.tvTitoloPdi);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitoloPdi.getText() + "'";
        }
    }
}
