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
    private Query queryImmaginePdi;
    private Query queryPdi;
    private Query queryRaggruppamentoPdi;
    private ArrayList<Categoria> listaCategoria;
    private ArrayList<ImmaginePdi> listaImmaginePdi;
    private ArrayList<Pdi> listaPdi;
    private ArrayList<RaggruppamentoPdi> listaRaggruppamentoPdi;
    private Boolean scaricamentoRiuscito;

    // variabili pubbliche
    public FirebaseHelperEseguiAlScaricamentoCompletato eseguiAlScaricamentoCompletato;

    //region Metodi privati

    // costruttore privato per costringere l'uso questa classe solo come singleton
    private FirebaseHelper() {
        super();

        scaricamentoRiuscito = false;

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        queryCategoria         = FirebaseDatabase.getInstance().getReference().child("CATEGORIA");
        queryImmaginePdi       = FirebaseDatabase.getInstance().getReference().child("IMMAGINE_PDI");
        queryPdi               = FirebaseDatabase.getInstance().getReference().child("PDI");
        queryRaggruppamentoPdi = FirebaseDatabase.getInstance().getReference().child("RAGGRUPPAMENTO_PDI");

        queryCategoria.keepSynced(true);
        queryImmaginePdi.keepSynced(true);
        queryPdi.keepSynced(true);
        queryRaggruppamentoPdi.keepSynced(true);
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

    void iniziaScaricareDb() {
        Log.d("DEBUGAPP", TAG + " [iniziaScaricareDb]");

        queryCategoria.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCategoria = new ArrayList<Categoria>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
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

                    listaCategoria.add(categoria);
                }

                Log.d("DEBUGAPP", TAG + " [iniziaScaricareDb - onDataChange] listaCategoria: " + listaCategoria);

                scaricaImmaginePdi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[iniziaScaricareDb - onCancelled] databaseError: " + databaseError.toException());

                scaricamentoRiuscito = false;

                if (eseguiAlScaricamentoCompletato != null) {
                    eseguiAlScaricamentoCompletato.esegui();
                }
            }
        });
    }

    private void scaricaImmaginePdi() {
        Log.d("DEBUGAPP", TAG + " [scaricaImmaginePdi]");

        queryImmaginePdi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaImmaginePdi = new ArrayList<ImmaginePdi>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImmaginePdi immaginePdi = new ImmaginePdi(
                            Integer.valueOf((String) snapshot.child("idImmaginePdi").getValue()),
                            Integer.valueOf((String) snapshot.child("idImmaginePdi_idPdi").getValue()),
                            Integer.valueOf((String) snapshot.child("ordinamento").getValue()),
                            (String) snapshot.child("url").getValue());

                    listaImmaginePdi.add(immaginePdi);
                }

                Log.d("DEBUGAPP", TAG + " [scaricaImmaginePdi - onDataChange] listaImmaginePdi: " + listaImmaginePdi);

                scaricaPdi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[scaricaImmaginePdi - onCancelled] databaseError: " + databaseError.toException());

                scaricamentoRiuscito = false;

                if (eseguiAlScaricamentoCompletato != null) {
                    eseguiAlScaricamentoCompletato.esegui();
                }
            }
        });
    }

    private void scaricaPdi() {
        Log.d("DEBUGAPP", TAG + " [scaricaPdi]");

        queryPdi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPdi = new ArrayList<Pdi>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Pdi pdi = new Pdi(
                            Integer.valueOf((String) snapshot.child("idPdi").getValue()),
                            Integer.valueOf((String) snapshot.child("idPdi_idCategoria").getValue()),
                            Integer.valueOf((String) snapshot.child("idPdi_idRaggruppamento").getValue()),
                            Integer.valueOf((String) snapshot.child("ordinamento").getValue()),
                                            (String) snapshot.child("titoloItaliano").getValue(),
                                            (String) snapshot.child("titoloInglese").getValue(),
                                            (String) snapshot.child("titoloTedesco").getValue(),
                                            (String) snapshot.child("titoloFrancese").getValue(),
                                            (String) snapshot.child("descrizioneItaliano").getValue(),
                                            (String) snapshot.child("descrizioneInglese").getValue(),
                                            (String) snapshot.child("descrizioneTedesco").getValue(),
                                            (String) snapshot.child("descrizioneFrancese").getValue(),
                                            (String) snapshot.child("citta").getValue(),
                                            (String) snapshot.child("via").getValue(),
                            Integer.valueOf((String) snapshot.child("numeroCivico").getValue()),
                                            (String) snapshot.child("interno").getValue(),
                            Integer.valueOf((String) snapshot.child("cap").getValue()),
                             Double.valueOf((String) snapshot.child("latitudine").getValue()),
                             Double.valueOf((String) snapshot.child("longitudine").getValue()),
                                            (String) snapshot.child("telefono").getValue(),
                                            (String) snapshot.child("fax").getValue(),
                                            (String) snapshot.child("cellulare").getValue(),
                                            (String) snapshot.child("email").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico1Italiano").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico1Inglese").getValue(),
                                            (String) snapshot.child("titoloLink1GenericoTedesco").getValue(),
                                            (String) snapshot.child("titoloLink1GenericoFrancese").getValue(),
                                            (String) snapshot.child("linkGenerico1").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico2Italiano").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico2Inglese").getValue(),
                                            (String) snapshot.child("titoloLink2GenericoTedesco").getValue(),
                                            (String) snapshot.child("titoloLink2GenericoFrancese").getValue(),
                                            (String) snapshot.child("linkGenerico2").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico3Italiano").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico3Inglese").getValue(),
                                            (String) snapshot.child("titoloLink3GenericoTedesco").getValue(),
                                            (String) snapshot.child("titoloLink3GenericoFrancese").getValue(),
                                            (String) snapshot.child("linkGenerico3").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico4Italiano").getValue(),
                                            (String) snapshot.child("titoloLinkGenerico4Inglese").getValue(),
                                            (String) snapshot.child("titoloLink4GenericoTedesco").getValue(),
                                            (String) snapshot.child("titoloLink4GenericoFrancese").getValue(),
                                            (String) snapshot.child("linkGenerico4").getValue(),
                                            (String) snapshot.child("fileTracciaGps").getValue(),
                                            (String) snapshot.child("urlTracciaGps").getValue());

                    listaPdi.add(pdi);
                }

                Log.d("DEBUGAPP", TAG + " [scaricaPdi - onDataChange] listaPdi: " + listaPdi);

                scaricaRaggruppamentoPdi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[scaricaPdi - onCancelled] databaseError: " + databaseError.toException());

                scaricamentoRiuscito = false;

                if (eseguiAlScaricamentoCompletato != null) {
                    eseguiAlScaricamentoCompletato.esegui();
                }
            }
        });
    }

    private void scaricaRaggruppamentoPdi() {
        Log.d("DEBUGAPP", TAG + " [scaricaRaggruppamentoPdi]");

        queryRaggruppamentoPdi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaRaggruppamentoPdi = new ArrayList<RaggruppamentoPdi>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    RaggruppamentoPdi raggruppamentoPdi = new RaggruppamentoPdi(
                            Integer.valueOf((String) snapshot.child("idRaggruppamentoPdi").getValue()),
                            Integer.valueOf((String) snapshot.child("ordinamento").getValue()),
                                            (String) snapshot.child("nomeRaggruppamentoItaliano").getValue(),
                                            (String) snapshot.child("nomeRaggruppamentoInglese").getValue(),
                                            (String) snapshot.child("nomeRaggruppamentoTedesco").getValue(),
                                            (String) snapshot.child("nomeRaggruppamentoFrancese").getValue());

                    listaRaggruppamentoPdi.add(raggruppamentoPdi);
                }

                Log.d("DEBUGAPP", TAG + " [scaricaRaggruppamentoPdi - onDataChange] listaRaggruppamentoPdi: " + listaRaggruppamentoPdi);

                scaricamentoRiuscito = true;

                if (eseguiAlScaricamentoCompletato != null) {
                    eseguiAlScaricamentoCompletato.esegui();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DEBUGAPP", TAG + "[scaricaRaggruppamentoPdi - onCancelled] databaseError: " + databaseError.toException());

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
