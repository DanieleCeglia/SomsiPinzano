package dcsoft.somsipinzano;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

            default: {
                txtTitle.setText(pdi.getTitoloInglese());
            }
        }

        TextView txtDescription = (TextView) mView.findViewById(R.id.bubble_description);
        txtDescription.setText(mainActivity.getResources().getString(R.string.tocca_qui_per_maggiori_dettagli));

        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.bubble_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
                alertDialogBuilder.setCancelable(true);

                alertDialogBuilder.setMessage(pdi.toString());

                alertDialogBuilder.setNeutralButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onClose() {
    }
}
