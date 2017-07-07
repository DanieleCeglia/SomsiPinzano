package dcsoft.somsipinzano;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

interface FirebaseHelperEseguiAlScaricamentoCompletato {
    void esegui();
}

class FirebaseHelper {
    // variabili private

    private final String TAG = getClass().getSimpleName();
    private static FirebaseHelper firebaseHelperCondiviso;
    private Query queryCategoria;
    private ArrayList<Categoria> listaCategorie;
    private Boolean scaricamentoRiuscito;

    // variabili pubbliche
    public FirebaseHelperEseguiAlScaricamentoCompletato eseguiAlScaricamentoCompletato;

    //region Metodi privati

    // costruttore privato per costringere l'uso questa classe solo come singleton
    private FirebaseHelper() {
        super();

        scaricamentoRiuscito = false;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        queryCategoria = databaseReference.child("CATEGORIA");
    }

    //endregion

    //region Metodi pubblici

    // singleton
    static synchronized FirebaseHelper dammiFirebaseHelperCondiviso() {
        if (firebaseHelperCondiviso == null) {
            firebaseHelperCondiviso = new FirebaseHelper();
        }

        return firebaseHelperCondiviso;
    }

    void scaricaDatabase() {
        Log.d("DEBUGAPP", TAG + " [scaricaDatabase]");

        queryCategoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCategorie = new ArrayList<>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Log.d("DEBUGAPP", TAG + " [onDataChange] snapshot: " + snapshot);

                    Categoria categoria = new Categoria(
                            Integer.valueOf((String) snapshot.child("idCategoria").getValue()),
                            Integer.valueOf((String) snapshot.child("ordinamento").getValue()),
                                            (String) snapshot.child("nomeItaliano").getValue(),
                                            (String) snapshot.child("nomeInglese").getValue(),
                                            (String) snapshot.child("nomeTedesco").getValue(),
                                            (String) snapshot.child("nomeFrancese").getValue(),
                                            (String) snapshot.child("descrizioneItaliano").getValue(),
                                            (String) snapshot.child("descrizioneInglese").getValue(),
                                            (String) snapshot.child("descrizioneTedesco").getValue(),
                                            (String) snapshot.child("descrizioneFrancese").getValue(),
                                            (String) snapshot.child("fileImmagine").getValue(),
                                            (String) snapshot.child("fileImmagineCopertina").getValue(),
                                            (String) snapshot.child("filePin").getValue());

                    listaCategorie.add(categoria);
                }

                Log.d("DEBUGAPP", TAG + " [onDataChange] listaCategorie: " + listaCategorie);

                scaricamentoRiuscito = true;

                if (eseguiAlScaricamentoCompletato != null) {
                    eseguiAlScaricamentoCompletato.esegui();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "xxx [onCancelled] databaseError: " + databaseError.toException());

                scaricamentoRiuscito = false;

                if (eseguiAlScaricamentoCompletato != null) {
                    eseguiAlScaricamentoCompletato.esegui();
                }
            }
        });
    }

    Boolean scaricamentoDatabaseRiuscitoConSuccesso() {
        return scaricamentoRiuscito;
    }

    //endregion
}
