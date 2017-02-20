package dcsoft.somsipinzano;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class OsmdroidMarkerInfoWindow extends InfoWindow {
    public MainActivity mainActivity;
    public Pdi pdi;

    public OsmdroidMarkerInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @Override
    public void onOpen(Object o) {
        TextView txtTitle = (TextView) mView.findViewById(R.id.bubble_title);
        switch (mainActivity.gestoreDatabaseCondiviso.getLingua()) {
            case "italiano": {
                txtTitle.setText(pdi.getTitoloItaliano());
            }
            break;

            case "Deutsch": {
                txtTitle.setText(pdi.getTitoloTedesco());
            }
            break;

            case "français": {
                txtTitle.setText(pdi.getTitoloFrancese());
            }
            break;

            default: {
                txtTitle.setText(pdi.getTitoloInglese());
            }
        }

        TextView txtDescription = (TextView) mView.findViewById(R.id.bubble_description);
        txtDescription.setText(mainActivity.getResources().getString(R.string.tocca_qui_per_maggiori_dettagli));

        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.bubble_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivity.apriDettaglioSuPdi(pdi);
            }
        });
    }

    @Override
    public void onClose() {
    }
}
